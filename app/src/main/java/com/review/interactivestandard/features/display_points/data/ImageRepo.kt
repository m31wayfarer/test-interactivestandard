package com.review.interactivestandard.features.display_points.data

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.review.interactivestandard.BuildConfig
import com.review.interactivestandard.features.display_points.domain.IImageRepo
import com.review.interactivestandard.features.display_points.domain.entity.Image
import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo
import com.review.interactivestandard.features.display_points.view.mappers.ImageMapper
import com.review.interactivestandard.utils.AppCoroutineDispatchers
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ImageRepo @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val dispatchers: AppCoroutineDispatchers,
    private val imageMapper: ImageMapper
) : IImageRepo {
    companion object {
        private const val CHART_IMAGE_NAME = "chart.jpg"
        private const val IMAGES_FOLDER = "images"
    }

    override suspend fun saveImageToShare(image: Image): ShareImageInfo? {
        return withContext(dispatchers.io) {
            val bitmap = withContext(dispatchers.computation) {
                imageMapper.mapToBitmap(image)
            }
            try {
                //clear previous images
                File(context.cacheDir, IMAGES_FOLDER).deleteRecursively()
                val cachePath = File(context.cacheDir, IMAGES_FOLDER)
                val imageFile = File(cachePath, CHART_IMAGE_NAME)
                cachePath.mkdirs()
                val stream = FileOutputStream(imageFile)
                stream.use {
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG, 100, stream
                    )
                }
                val contentUri =
                    FileProvider.getUriForFile(
                        context,
                        "${BuildConfig.APPLICATION_ID}.fileProvider",
                        imageFile
                    )
                ShareImageInfo(uriString = contentUri.toString(), imageType = "image/jpeg")
            } catch (ex: IOException) {
                Timber.e(ex, "saveImage error")
                null
            }
        }
    }
}