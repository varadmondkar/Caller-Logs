package com.varad.callerlogs.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CallDao {

    @Insert
    suspend fun insertCall(call: IncomingCall)

    @Query("SELECT * FROM incoming_calls ORDER BY timestamp DESC")
    suspend fun getAllCalls(): List<IncomingCall>

    @Query("SELECT * FROM incoming_calls ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastCall(): List<IncomingCall>

    @Query("SELECT DISTINCT phoneNumber, id, timestamp FROM incoming_calls WHERE phoneNumber=:number ORDER BY timestamp DESC")
    suspend fun getDistinctCalls(number: String): List<IncomingCall>

    @Query("SELECT COUNT(phoneNumber) FROM incoming_calls WHERE phoneNumber=:number ORDER BY timestamp DESC")
    suspend fun getCallCount(number: String): Int
}