package com.review.interactivestandard.features.display_points.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class GetPointsResponse(val points: List<PointDTO>)