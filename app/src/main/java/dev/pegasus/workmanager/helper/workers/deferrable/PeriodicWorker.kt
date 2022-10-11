package dev.pegasus.workmanager.helper.workers.deferrable

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import dev.pegasus.workmanager.R
import dev.pegasus.workmanager.helper.utils.GeneralUtils.TAG
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PeriodicWorker(appContext: Context, workerParameters: WorkerParameters) : Worker(appContext, workerParameters) {

    override fun doWork(): Result {

        val simpleDateFormat = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
        val date = Date(System.currentTimeMillis())
        val time = simpleDateFormat.format(date)

        Log.d(TAG, "doWork: $time")
        createNotificationChannel()
        buildNotification(time)

        return Result.success()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("CHANNEL_ID", "Updates", NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Periodically"
            }
            // Register the channel with the system
            val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(message: String) {
        val builder = NotificationCompat.Builder(applicationContext, "CHANNEL_ID")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Hello Developers")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManagerCompat = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManagerCompat.notify(100, builder.build())
    }
}