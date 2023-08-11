package com.review.interactivestandard.features.display_points.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.review.interactivestandard.R
import com.review.interactivestandard.features.display_points.domain.IImageInteractor
import com.review.interactivestandard.features.display_points.domain.entity.Image
import com.review.interactivestandard.features.display_points.view.dto.DisplayPointsSingleEvent
import com.review.interactivestandard.features.display_points.view.dto.DisplayPointsViewState
import com.review.interactivestandard.features.display_points.view.mappers.TableViewPointMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import com.review.interactivestandard.utils.IResourceHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayPointsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatchers: AppCoroutineDispatchers,
    private val imageInteractor: IImageInteractor,
    private val resourceHelper: IResourceHelper,
    mapper: TableViewPointMapper
) :
    ViewModel() {

    private val _state = MutableStateFlow(
        DisplayPointsViewState(
            chartPoints = emptyList(),
            tablePoints = emptyList(),
        )
    )
    val state = _state.asStateFlow()

    private val _event = MutableStateFlow<DisplayPointsSingleEvent?>(null)

    val event = _event.asStateFlow()

    init {
        viewModelScope.launch(dispatchers.computation) {
            val points = DisplayPointsFragmentArgs.fromSavedStateHandle(
                savedStateHandle
            ).points.toList()
            _state.update { currentState ->
                currentState.copy(chartPoints = points.sortedBy { point -> point.x },
                    tablePoints = points.map { point -> mapper.mapToTableViewPoint(point) })
            }
        }
    }

    fun shareImage(image: Image) {
        viewModelScope.launch {
            val shareImageInfo = imageInteractor.saveImageToShare(image)

            if (shareImageInfo == null) {
                _event.update {
                    DisplayPointsSingleEvent.ShowError(
                        message = resourceHelper.getStringByResId(
                            R.string.error_during_sharing_chart_image
                        )
                    )
                }
            } else {
                _event.update {
                    DisplayPointsSingleEvent.ShareImage(
                        shareImageInfo = shareImageInfo,
                        chooserTitle = resourceHelper.getStringByResId(
                            R.string.chooser_title_for_sharing_chart_image
                        )
                    )
                }
            }
        }
    }

    fun didHandleEvent() {
        _event.update { null }
    }
}