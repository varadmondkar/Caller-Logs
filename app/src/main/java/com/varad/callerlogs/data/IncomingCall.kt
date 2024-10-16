package com.varad.callerlogs.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "incoming_calls")
data class IncomingCall(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val phoneNumber: String,
    val timestamp: Long
)
