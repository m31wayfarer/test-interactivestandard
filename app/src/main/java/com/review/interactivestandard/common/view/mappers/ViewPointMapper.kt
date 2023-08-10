package com.review.interactivestandard.common.view.mappers

import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.view.dto.ViewPointDTO
import javax.inject.Inject

class ViewPointMapper @Inject constructor() {
    fun mapToView(point: EntityPoint) = ViewPointDTO(x = point.x, y = point.y)
}