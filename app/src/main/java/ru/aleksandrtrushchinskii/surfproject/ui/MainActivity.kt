package ru.aleksandrtrushchinskii.surfproject.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.create_edit_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.dateFormat
import ru.aleksandrtrushchinskii.surfproject.common.tools.timeFormat
import ru.aleksandrtrushchinskii.surfproject.common.tools.visible
import ru.aleksandrtrushchinskii.surfproject.databinding.MainActivityBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import java.util.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    @Inject
    lateinit var navigation: Navigation

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var todoViewModel: TodoViewModel
    private lateinit var binding: MainActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel = ViewModelProviders.of(this, viewModelFactory).get(TodoViewModel::class.java)

        binding = DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity).apply {
            setLifecycleOwner(this@MainActivity)
        }

        if (savedInstanceState == null){
            navigation.start()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()

        if (todoViewModel.todo.value?.notification != null) {
            calendar.time = todoViewModel.todo.value?.notification
        }

        with(calendar){
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        todoViewModel.todo.value!!.notification = calendar.time

        todoNotificationDateTextView?.text = dateFormat.format(calendar.time)

        todoNotificationClearButton?.visible()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()

        if (todoViewModel.todo.value?.notification != null) {
            calendar.time = todoViewModel.todo.value?.notification
        }

        with(calendar){
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }

        todoViewModel.todo.value!!.notification = calendar.time

        todoNotificationTimeTextView?.text = timeFormat.format(calendar.time)

        todoNotificationClearButton?.visible()
    }

}