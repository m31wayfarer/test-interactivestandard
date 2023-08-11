package com.review.interactivestandard.features.display_points.view.dto

import com.review.interactivestandard.features.display_points.domain.entity.ShareImageInfo

sealed interface DisplayPointsSingleEvent {
    data class ShareImage(val shareImageInfo: ShareImageInfo, val chooserTitle: String) :
        DisplayPointsSingleEvent

    data class ShowError(val message: String) : DisplayPointsSingleEvent
}