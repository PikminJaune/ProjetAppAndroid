package com.tp_apps.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val href: String = "",
    val status: String = "",
    val ticketNumber: String = "",
    val createdDate: String = "",
    val priority: String = "",
    val customer: Customer = Customer()
)
