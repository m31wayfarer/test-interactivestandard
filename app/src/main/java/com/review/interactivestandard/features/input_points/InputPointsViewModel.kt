package com.review.interactivestandard.features.input_points

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.review.interactivestandard.R
import com.review.interactivestandard.common.domain.entity.EntityResult
import com.review.interactivestandard.common.view.mappers.ViewPointMapper
import com.review.interactivestandard.features.input_points.domain.IPointsInteractor
import com.review.interactivestandard.features.input_points.dto.InputPointsSingleEvent
import com.review.interactivestandard.features.input_points.dto.InputPointsViewState
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import com.review.interactivestandard.utils.IResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputPointsViewModel @Inject constructor(
    private val pointsInteractor: IPointsInteractor,
    private val resourceHelper: IResourceHelper,
    private val viewPointMapper: ViewPointMapper,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {
    private val _state = MutableStateFlow(
        InputPointsViewState(
            isLoading = false,
            event = null
        )
    )
    val state = _state.asStateFlow()

    fun wantsToGetPoints(countString: String) {
        if (_state.value.isLoading)
            return
        val count = try {
            countString.toInt()
        } catch (ex: NumberFormatException) {
            -1
        }
        if (count <= 0) {
            _state.update { currentValue ->
                currentValue.copy(
                    event = InputPointsSingleEvent.ShowError(
                        message = resourceHelper.getStringByResId(
                            R.string.error_when_count_points_invalid
                        )
                    )
                )
            }
        } else {
            getPoints(count = count)
        }
    }

    private fun getPoints(count: Int) {
        viewModelScope.launch(dispatchers.computation) {
            pointsInteractor.getPoints(count)
                .take(1)
                .onStart {
                    _state.update { currentValue ->
                        currentValue.copy(isLoading = true)
                    }
                }
                .onCompletion {
                    _state.update { currentValue ->
                        currentValue.copy(isLoading = false)
                    }
                }
                .collectLatest { result ->
                    when (result) {
                        is IPointsInteractor.GetPointsResult.Fail ->
                            handlePointsError(result.errorType)

                        is IPointsInteractor.GetPointsResult.Success -> {
                            _state.update { currentValue ->
                                currentValue.copy(
                                    isLoading = false,
                                    event = InputPointsSingleEvent.DisplayPoints(points =
                                    result.points.map { point -> viewPointMapper.mapToView(point) })
                                )
                            }
                        }
                    }
                }
        }
    }

    private fun handlePointsError(errorType: EntityResult.ErrorType) {
        val message = when (errorType) {
            is EntityResult.ErrorType.GenericError -> when {
                errorType.error != null -> errorType.error.message
                else ->
                    resourceHelper.getStringByResId(R.string.unexpected_error_during_getting_points)
            }

            EntityResult.ErrorType.NetworkError ->
                resourceHelper.getStringByResId(R.string.error_network)
        }
        _state.update { currentValue ->
            currentValue.copy(
                isLoading = false,
                event = InputPointsSingleEvent.ShowError(message = message)
            )
        }
    }

    fun didNavigateEvent() {
        _state.update { currentValue ->
            currentValue.copy(event = null)
        }
    }
}