package com.review.interactivestandard.features.input_points.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
class GetPointsResponse(val points: List<PointDTO>)