package ru.aleksandrtrushchinskii.surfproject.common.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.NOTIFICATION_CHANNEL_ID
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_DESCRIPTION_EXTRA
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_TITLE_EXTRA
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug


class TimeNotification : BroadcastReceiver() {
    
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return

        logDebug("Notify user about ${intent.getStringExtra(TODO_TITLE_EXTRA)}")

        createNotificationChannel(context)

        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_clipboard_outline)
                .setContentTitle(intent.getStringExtra(TODO_TITLE_EXTRA))
                .setContentText(intent.getStringExtra(TODO_DESCRIPTION_EXTRA))
                .setVibrate(longArrayOf(1000))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationService = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationService.notify(123, notificationBuilder.build())
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "channel-name"
            val description = "channel-description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}