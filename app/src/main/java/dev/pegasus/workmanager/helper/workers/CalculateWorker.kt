package dev.pegasus.workmanager.helper.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.pegasus.workmanager.helper.utils.GeneralUtils.TAG

class CalculateWorker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {

    override fun doWork(): Result {

        var n = 0
        for (i in 1..10_00_000) {
            n += i
        }
        Log.d(TAG, "doWork: $n")

        return Result.success()
    }
}