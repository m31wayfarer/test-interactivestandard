package com.review.interactivestandard.features.display_points.view.mappers

import com.review.interactivestandard.common.view.dto.ViewPointDTO
import com.review.interactivestandard.features.display_points.view.dto.TableViewPointDTO
import javax.inject.Inject

class TableViewPointMapper @Inject constructor() {
    fun mapToTableViewPoint(point: ViewPointDTO) =
        TableViewPointDTO(x = point.x.toString(), y = point.y.toString())
}