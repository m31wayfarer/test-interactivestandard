package com.review.interactivestandard.features.display_points.data.remote

import com.review.interactivestandard.common.data.dto.toEntityResult
import com.review.interactivestandard.common.data.safeApiCall
import com.review.interactivestandard.common.domain.entity.EntityPoint
import com.review.interactivestandard.common.domain.entity.EntityResult
import com.review.interactivestandard.features.display_points.data.remote.mappers.RemotePointMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import dagger.Lazy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PointsRemoteSource @Inject constructor(
    private val apiService: Lazy<PointsApiService>,
    private val dispatchers: AppCoroutineDispatchers,
    private val remotePointMapper: RemotePointMapper
) :
    IPointsRemoteSource {
    override fun getPoints(count: Int): Flow<EntityResult<List<EntityPoint>>> {
        return flow {
            emit(safeApiCall {
                apiService.get().getPoints(count)
            }.toEntityResult { resp ->
                resp.points.map { point ->
                    remotePointMapper.mapToEntity(point)
                }
            })
        }
            .flowOn(dispatchers.computation)
    }
}