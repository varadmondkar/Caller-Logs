package com.varad.callerlogs.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PeriodicWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        Log.d("IncomingCallReceiver", "PeriodicWorker: $inputData")
        return Result.success()
    }
}