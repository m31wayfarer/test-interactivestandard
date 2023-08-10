package com.review.interactivestandard.features.input_points.dto

import com.review.interactivestandard.common.view.dto.ViewPointDTO

data class InputPointsViewState(
    val isLoading: Boolean = false,
    val event: InputPointsSingleEvent? = null
)

sealed interface InputPointsSingleEvent {
    data class DisplayPoints(val points: List<ViewPointDTO>) : InputPointsSingleEvent
    data class ShowError(val message: String) : InputPointsSingleEvent
}