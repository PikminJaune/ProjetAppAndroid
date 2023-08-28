package com.tp_apps.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class NetworkNode(
    val name:String = "",
    val connection : Connection = Connection()
)
