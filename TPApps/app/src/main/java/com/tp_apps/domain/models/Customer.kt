package com.tp_apps.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
    val href: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val coord: Coordinate = Coordinate(),
    val email: String = "",
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val postalCode: String = "",
    val phone: String = "",
    val gateways : List<Gateway> = listOf()
)
