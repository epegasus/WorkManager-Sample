package dev.pegasus.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dev.pegasus.workmanager.databinding.FragmentImmediateBinding
import dev.pegasus.workmanager.helper.workers.CalculateWorker
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
        WorkManager.getInstance(globalContext).enqueue(workRequest1)
        WorkManager.getInstance(globalContext).enqueue(workRequest2)
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

}