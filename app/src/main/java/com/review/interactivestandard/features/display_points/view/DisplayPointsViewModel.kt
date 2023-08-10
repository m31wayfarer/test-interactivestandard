package com.review.interactivestandard.features.display_points.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.review.interactivestandard.features.display_points.view.dto.DisplayPointsViewState
import com.review.interactivestandard.features.display_points.view.mappers.TableViewPointMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayPointsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    dispatchers: AppCoroutineDispatchers,
    mapper: TableViewPointMapper
) :
    ViewModel() {

    private val _state = MutableStateFlow(
        DisplayPointsViewState(
            chartPoints = emptyList(),
            tablePoints = emptyList()
        )
    )
    val state = _state.asStateFlow()

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
}