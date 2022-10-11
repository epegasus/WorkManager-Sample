package dev.pegasus.workmanager.ui.fragments.deferrable

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.pegasus.workmanager.R
import dev.pegasus.workmanager.databinding.FragmentDeferrableBinding
import dev.pegasus.workmanager.helper.utils.GeneralUtils.TAG
import dev.pegasus.workmanager.helper.workers.deferrable.PeriodicWorker
import dev.pegasus.workmanager.ui.fragments.BaseFragment
import java.util.Calendar
import java.util.concurrent.TimeUnit

class FragmentDeferrable : BaseFragment<FragmentDeferrableBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return getView(inflater, container, R.layout.fragment_deferrable)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val worker = getPeriodicWorker()
        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
            "Timer",
            ExistingPeriodicWorkPolicy.KEEP,
            worker
        )
        observeOutput(worker)

        val temp = "Started Worker Request"
        binding.tvResultDeferrable.text = temp
    }

    private fun getPeriodicWorker(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
            .build()
    }

    private fun getScheduledPeriodicWorker(): PeriodicWorkRequest {
        // Start from 6 am
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR, 6)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.AM_PM, 0)

        val now = Calendar.getInstance()
        val date1 = calendar.time.time
        val date2 = now.time.time

        val seconds = (date1 - date2) / 1000
        return PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES)
            .setInitialDelay(seconds, TimeUnit.SECONDS)
            .build()
    }

    private fun observeOutput(dataWorker: PeriodicWorkRequest) {
        WorkManager.getInstance(appContext).getWorkInfoByIdLiveData(dataWorker.id).observe(viewLifecycleOwner) {
            Log.d(TAG, "observeOutput: ${it.state}")
            if (isAdded)
                binding.tvResultDeferrable.text = it.state.toString()
        }
    }
}