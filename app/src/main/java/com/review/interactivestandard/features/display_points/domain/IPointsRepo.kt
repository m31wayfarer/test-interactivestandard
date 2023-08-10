package com.review.interactivestandard.features.display_points.domain

import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.domain.entity.EntityResult
import kotlinx.coroutines.flow.Flow

interface IPointsRepo {
    fun getPoints(count: Int): Flow<EntityResult<List<EntityPoint>>>
}