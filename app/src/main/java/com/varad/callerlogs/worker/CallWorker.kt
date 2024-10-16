package com.varad.callerlogs.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.varad.callerlogs.data.CallerDatabase
import com.varad.callerlogs.data.IncomingCall

class CallWorker(private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val phoneNumber = inputData.getString("phone_number") ?: return Result.failure()

        val callDao = CallerDatabase.getDatabase(applicationContext).callDao()
        callDao.insertCall(
            IncomingCall(
                phoneNumber = phoneNumber,
                timestamp = System.currentTimeMillis()
            )
        )
        return Result.success()
    }
}