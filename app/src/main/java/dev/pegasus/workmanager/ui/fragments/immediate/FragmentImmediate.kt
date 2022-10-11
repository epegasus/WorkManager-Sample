package dev.pegasus.workmanager.ui.fragments.immediate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import dev.pegasus.workmanager.R
import dev.pegasus.workmanager.databinding.FragmentImmediateBinding
import dev.pegasus.workmanager.helper.utils.GeneralUtils.TAG
import dev.pegasus.workmanager.helper.workers.CalculateWorker
import dev.pegasus.workmanager.helper.workers.DataCalculatorWorker
import dev.pegasus.workmanager.ui.fragments.BaseFragment

class FragmentImmediate : BaseFragment<FragmentImmediateBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return getView(inflater, container, R.layout.fragment_immediate)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Two Ways to create a request
        val workRequest1 = buildRequestTypeOne()
        val workRequest2 = buildRequestTypeTwo()
        val dataWorker = buildDataWorker()

        with(WorkManager.getInstance(appContext)) {
            enqueue(workRequest1)
            enqueue(workRequest2)
            enqueue(dataWorker)
            observeOutput(dataWorker)
        }
    }

    private fun observeOutput(dataWorker: OneTimeWorkRequest) {
        WorkManager.getInstance(appContext).getWorkInfoByIdLiveData(dataWorker.id).observe(viewLifecycleOwner) {
            when (it.state) {
                WorkInfo.State.BLOCKED -> { Log.d(TAG, "observeOutput: BLOCKED") }
                WorkInfo.State.CANCELLED -> { Log.d(TAG, "observeOutput: CANCELLED") }
                WorkInfo.State.ENQUEUED -> { Log.d(TAG, "observeOutput: ENQUEUED") }
                WorkInfo.State.FAILED -> { Log.d(TAG, "observeOutput: FAILED") }
                WorkInfo.State.RUNNING -> { Log.d(TAG, "observeOutput: RUNNING") }
                WorkInfo.State.SUCCEEDED -> {
                    Log.d(TAG, "observeOutput: SUCCEEDED")
                    val result = it.outputData.getInt("results", 0)
                    binding.tvStatusImmediate.text = result.toString()
                }
            }
            if (it.state.isFinished) {
                Log.d(TAG, "observeOutput: isFinished")
            }
        }
    }

    private fun buildRequestTypeOne(): OneTimeWorkRequest {
        // Static method (Can use when no additional configuration needed)
        return OneTimeWorkRequest.from(CalculateWorker::class.java)
    }

    private fun buildRequestTypeTwo(): OneTimeWorkRequest {
        // Builder (Can use for more complex work)
        return OneTimeWorkRequestBuilder<CalculateWorker>()
            // Additional Configurations
            .build()
    }

    private fun buildDataWorker(): OneTimeWorkRequest {
        val workDataOf = workDataOf(
            "a" to 10,
            "b" to 20,
            "c" to 30
        )
        return OneTimeWorkRequestBuilder<DataCalculatorWorker>()
            .setInputData(workDataOf)
            .build()
    }
}