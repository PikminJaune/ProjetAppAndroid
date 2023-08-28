package com.tp_apps.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Borne(
    val serialNumber: String = "",
    val revision: String = "",
    val pin: String = "",
    val hash: String = ""
)
