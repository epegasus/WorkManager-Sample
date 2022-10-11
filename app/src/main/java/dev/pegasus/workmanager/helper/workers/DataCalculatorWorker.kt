package dev.pegasus.workmanager.helper.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class DataCalculatorWorker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {

    override fun doWork(): Result {

        val a = inputData.getInt("a", 0)
        val b = inputData.getInt("b", 0)
        val c = inputData.getInt("c", 0)

        val result = a + b + c
        val data = workDataOf("results" to result)

        return Result.success(data)
    }
}