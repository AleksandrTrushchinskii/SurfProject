package ru.aleksandrtrushchinskii.surfproject.common.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_DESCRIPTION_EXTRA
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_TITLE_EXTRA
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


class Alarm(private val context: Context, private val database: TodoDatabase) {

    private val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager


    fun setAlarmForAll() = launch {
        database.getSoonTodos().forEach {
            val pendingIntent = createPendingIntent(it)
            alarmManager.cancel(pendingIntent)
            alarmManager.set(AlarmManager.RTC, it.notification!!.time, pendingIntent)
        }
    }

    fun resetAlarm(todo: Todo) = launch {
        val pendingIntent = createPendingIntent(todo)
        alarmManager.cancel(pendingIntent)
        alarmManager.set(AlarmManager.RTC, todo.notification!!.time, pendingIntent)
    }

    fun cancel(todo: Todo) {
        val pendingIntent = createPendingIntent(todo)
        alarmManager.cancel(pendingIntent)
    }

    private fun createPendingIntent(todo: Todo): PendingIntent {
        val intent = Intent(context, TimeNotification::class.java).apply {
            putExtra(TODO_TITLE_EXTRA, todo.title)
            putExtra(TODO_DESCRIPTION_EXTRA, todo.description)
        }

        val id = todo.id.hashCode()

        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

}