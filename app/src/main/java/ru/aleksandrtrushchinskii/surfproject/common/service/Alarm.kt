package ru.aleksandrtrushchinskii.surfproject.common.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.tools.NOTIFY_CODE
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_DESCRIPTION_EXTRA
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_TITLE_EXTRA
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase


class Alarm(private val context: Context, private val database: TodoDatabase) {

    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    
    fun resetNotify() = launch {
        val todo = database.getSoon() ?: return@launch

        val intent = Intent(context, TimeNotification::class.java)
        intent.putExtra(TODO_TITLE_EXTRA, todo.title)
        intent.putExtra(TODO_DESCRIPTION_EXTRA, todo.description)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFY_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        alarmManager.cancel(pendingIntent)

        alarmManager.set(AlarmManager.RTC, todo.notification!!.time, pendingIntent)

        logDebug("Set alarm for ${todo.title}")
    }

}