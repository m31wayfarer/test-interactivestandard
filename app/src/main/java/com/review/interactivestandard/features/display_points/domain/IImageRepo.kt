package com.review.interactivestandard.features.display_points.domain

import com.review.interactivestandard.features.display_points.domain.entity.Image
import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo

interface IImageRepo {
    /**
     * Method which save image to file and provide share info
     */
    suspend fun saveImageToShare(image: Image): ShareImageInfo?
}