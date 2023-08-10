package com.review.interactivestandard.features.display_points.data

import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.domain.entity.EntityResult
import com.review.interactivestandard.features.display_points.data.remote.IPointsRemoteSource
import com.review.interactivestandard.features.display_points.domain.IPointsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PointsRepo @Inject constructor(private val remote: IPointsRemoteSource) : IPointsRepo {
    override fun getPoints(count: Int): Flow<EntityResult<List<EntityPoint>>> {
        return remote.getPoints(count)
    }
}