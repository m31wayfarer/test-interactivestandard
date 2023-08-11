package com.review.interactivestandard.features.display_points.domain

import com.review.interactivestandard.features.display_points.domain.entity.Image
import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo
import javax.inject.Inject

class ImageInteractor @Inject constructor(private val imageRepo: IImageRepo) : IImageInteractor {
    override suspend fun saveImageToShare(image: Image): ShareImageInfo? {
        return imageRepo.saveImageToShare(image)
    }
}