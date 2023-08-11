package com.review.interactivestandard.features.display_points.view.mappers

import android.graphics.Bitmap
import com.review.interactivestandard.features.display_points.domain.entity.Image
import java.nio.ByteBuffer
import javax.inject.Inject

class ImageMapper @Inject constructor() {
    fun mapToImage(bitmap: Bitmap): Image {
        val size = bitmap.byteCount

        //allocate new instances which will hold bitmap
        val buffer = ByteBuffer.allocate(size)
        val bytes = ByteArray(size)

        //copy the bitmap's pixels into the specified buffer
        bitmap.copyPixelsToBuffer(buffer)

        //rewinds buffer (buffer position is set to zero and the mark is discarded)
        buffer.rewind()

        //transfer bytes from buffer into the given destination array
        buffer.get(bytes)

        return Image(bitmap.width, bitmap.height, bytes)
    }

    fun mapToBitmap(image: Image): Bitmap {
        val configBmp = Bitmap.Config.valueOf(Bitmap.Config.ARGB_8888.name)
        val bitmapTmp = Bitmap.createBitmap(image.width, image.height, configBmp)
        val buffer = ByteBuffer.wrap(image.data)
        bitmapTmp.copyPixelsFromBuffer(buffer)
        return bitmapTmp
    }
}