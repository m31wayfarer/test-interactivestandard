package com.review.interactivestandard.features.display_points.domain

import com.review.interactivestandard.common.domain.entity.EntityResult
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class PointsInteractor @Inject constructor(
    private val pointsRepo: IPointsRepo,
    private val dispatchers: AppCoroutineDispatchers
) : IPointsInteractor {
    override fun getPoints(count: Int): Flow<IPointsInteractor.GetPointsResult> {
        return pointsRepo.getPoints(count).map { result ->
            when (result) {
                is EntityResult.Error -> {
                    requireNotNull(result.error)
                    IPointsInteractor.GetPointsResult.Fail(result.error)
                }

                is EntityResult.Success -> {
                    requireNotNull(result.data)
                    IPointsInteractor.GetPointsResult.Success(
                        points =
                        result.data
                    )
                }
            }
        }.catch { th ->
            Timber.e(th, "getPoints error")
            emit(
                IPointsInteractor.GetPointsResult.Fail(
                    EntityResult.ErrorType.GenericError(throwable = th)
                )
            )
        }
            .flowOn(dispatchers.computation)
    }
}