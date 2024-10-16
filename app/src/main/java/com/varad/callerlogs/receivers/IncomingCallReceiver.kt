package com.varad.callerlogs.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.telephony.TelephonyManager
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.varad.callerlogs.service.CallForegroundService
import com.varad.callerlogs.worker.CallWorker
import com.varad.callerlogs.worker.PeriodicWorker
import java.util.concurrent.TimeUnit


class IncomingCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("IncomingCallReceiver", "onReceive: ${intent?.action}")
        if (intent?.action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {

            if (intent?.getStringExtra(TelephonyManager.EXTRA_STATE) == TelephonyManager.EXTRA_STATE_RINGING) {

                val number = intent?.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                Log.d("IncomingCallReceiver", "Incoming call from: $number")

                context?.let {
                    val serviceIntent = Intent(it, CallForegroundService::class.java)
                    if (SDK_INT >= Build.VERSION_CODES.O)
                        it.startForegroundService(serviceIntent)

//                    schedulePeriodicWorker(it)
                    scheduleWorkerToSoreCallDetails(it, number)
                }
            }
        }
    }

    // Schedule a WorkManager task to store call details
    private fun scheduleWorkerToSoreCallDetails(context: Context, phoneNumber: String?) {
        val workRequest = OneTimeWorkRequestBuilder<CallWorker>()
            .setInputData(workDataOf("phone_number" to phoneNumber))
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

    // Schedule a WorkManager task to store call details
    private fun schedulePeriodicWorker(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(10, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }

}