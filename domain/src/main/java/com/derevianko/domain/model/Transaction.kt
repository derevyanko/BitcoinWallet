package com.derevianko.domain.model

data class Transaction(
    val id: Int,
    val value: Float,
    val isDeposit: Boolean,
    val category: String?,
    val date: Long
)