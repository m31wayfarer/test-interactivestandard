package com.review.interactivestandard.features.display_points.data.remote.mappers

import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.features.display_points.data.remote.dto.PointDTO
import javax.inject.Inject

class RemotePointMapper @Inject constructor() {
    fun mapToEntity(point: PointDTO): EntityPoint = EntityPoint(x = point.x, y = point.y)
}