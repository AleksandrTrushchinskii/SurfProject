package ru.aleksandrtrushchinskii.surfproject.common.tools

import java.text.SimpleDateFormat


const val RC_SIGN_IN = 53
const val TODO_ID_KEY = "todo-id-key"
const val NOTIFY_CODE = 33
const val TODO_TITLE_EXTRA = "todo-title-extra"
const val TODO_DESCRIPTION_EXTRA = "todo-description-extra"
const val NOTIFICATION_CHANNEL_ID = "notify-channel-id"
const val DATE_TEXT = "date-text"
const val TIME_TEXT = "time-text"

private const val datePattern = "dd.MM.yyyy"
private const val timePattern = "HH:mm"
val fullDateFormat = SimpleDateFormat("$datePattern $timePattern")
val dateFormat = SimpleDateFormat(datePattern)
val timeFormat = SimpleDateFormat(timePattern)