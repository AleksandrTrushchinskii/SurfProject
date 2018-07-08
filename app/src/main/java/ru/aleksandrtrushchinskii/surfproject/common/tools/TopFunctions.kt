package ru.aleksandrtrushchinskii.surfproject.common.tools

import android.support.v4.app.FragmentManager
import android.widget.Button
import android.widget.TextView
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.DatePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.TimePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import java.util.*

fun configurateDate(fragmentManager: FragmentManager,
                    viewModel: TodoViewModel,
                    dateTV: TextView,
                    timeTV: TextView,
                    dateB: Button? = null,
                    timeB: Button? = null,
                    clearB: Button? = null) {
    if (viewModel.todo.value?.notification != null) {
        val c = Calendar.getInstance()

        c.time = viewModel.todo.value?.notification

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        if (year != null && month != null && month != day) {
            dateTV.text = "$day:$month:$year"
        }

        if (hour != null && minute != null) {
            timeTV.text = "$hour:$minute"
        }
    }else {
        clearB?.invisible()
    }

    dateB?.setOnClickListener {
        val newFragment = DatePickerFragment()
        newFragment.listener = { year, month, dayOfMonth ->
            val calendar = Calendar.getInstance()

            if (viewModel.todo.value?.notification != null) {
                calendar.time = viewModel.todo.value?.notification
            }

            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            viewModel.todo.value!!.notification = calendar.time

            dateTV.text = "$dayOfMonth:$month:$year"

            clearB?.visible()
        }
        newFragment.show(fragmentManager, "datePicker")
    }

    timeB?.setOnClickListener {
        val newFragment = TimePickerFragment()

        newFragment.listener = { hourOfDay, minute ->
            val calendar = Calendar.getInstance()

            if (viewModel.todo.value?.notification != null) {
                calendar.time = viewModel.todo.value?.notification
            }

            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendar.set(Calendar.MINUTE, minute)

            viewModel.todo.value!!.notification = calendar.time

            timeTV.text = "$hourOfDay:$minute"

            clearB?.visible()
        }

        newFragment.show(fragmentManager, "timePicker")
    }

    clearB?.setOnClickListener {
        viewModel.todo.value?.notification = null
        dateTV.text = ""
        timeTV.text = ""
    }
}