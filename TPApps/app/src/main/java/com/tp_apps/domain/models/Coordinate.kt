package com.tp_apps.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Coordinate(
    val latitude: Float = 0f,
    val longitude: Float = 0f
)
