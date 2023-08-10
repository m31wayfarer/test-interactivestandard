package com.review.interactivestandard.features.display_points.view.dto

import com.review.interactivestandard.common.view.dto.ViewPointDTO

data class DisplayPointsViewState(
    val chartPoints: List<ViewPointDTO>,
    val tablePoints: List<TableViewPointDTO>
)