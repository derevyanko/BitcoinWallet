package com.derevianko.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionDbEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val value: Float,
    val isDeposit: Boolean,
    val category: String?,
    val date: Long,
)
