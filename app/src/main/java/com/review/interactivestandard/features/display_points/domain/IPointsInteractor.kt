package com.review.interactivestandard.features.display_points.domain

import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.domain.entity.EntityResult
import kotlinx.coroutines.flow.Flow

interface IPointsInteractor {
    sealed interface GetPointsResult {
        data class Success(val points: List<EntityPoint>) : GetPointsResult
        data class Fail(val errorType: EntityResult.ErrorType) : GetPointsResult
    }

    fun getPoints(count: Int): Flow<GetPointsResult>
}