package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.create_edit_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.ACTION_KEY
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.common.tools.invisible
import ru.aleksandrtrushchinskii.surfproject.common.tools.visible
import ru.aleksandrtrushchinskii.surfproject.databinding.CreateEditFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.DatePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.TimePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import java.util.*
import javax.inject.Inject


class CreateEditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: CreateEditFragmentBinding
    private lateinit var viewModel: TodoViewModel

    private var datePickerFragment: DatePickerFragment? = null
    private var timePickerFragment: TimePickerFragment? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)

        binding = container.inflateBinding(R.layout.create_edit_fragment)
        binding.setLifecycleOwner(this)
        binding.todoViewModel = viewModel
        binding.action = arguments!!.getString(ACTION_KEY)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!!.getString(ACTION_KEY)!! == "create") {
            viewModel.todo.value = Todo()
        }

        configurateDate()

        LoadingState.stop()
    }

    private fun configurateDate() {
        if (viewModel.todo.value?.notification != null) {
            val c = Calendar.getInstance()

            c.time = viewModel.todo.value?.notification

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            if (year != null && month != null && month != day) {
                todoNotificationDateTextView.text = "$day:$month:$year"
            }

            if (hour != null && minute != null) {
                todoNotificationTimeTextView.text = "$hour:$minute"
            }
        } else {
            todoNotificationClearButton?.invisible()
        }

        todoNotificationDateButton?.setOnClickListener {
            datePickerFragment = DatePickerFragment()
            datePickerFragment?.listener = { year, month, dayOfMonth ->
                val calendar = Calendar.getInstance()

                if (viewModel.todo.value?.notification != null) {
                    calendar.time = viewModel.todo.value?.notification
                }

                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewModel.todo.value!!.notification = calendar.time

                todoNotificationDateTextView.text = "$dayOfMonth:$month:$year"

                todoNotificationClearButton?.visible()
            }
            datePickerFragment?.show(fragmentManager, "datePicker")
        }

        todoNotificationTimeButton?.setOnClickListener {
            timePickerFragment = TimePickerFragment()

            timePickerFragment?.listener = { hourOfDay, minute ->
                val calendar = Calendar.getInstance()

                if (viewModel.todo.value?.notification != null) {
                    calendar.time = viewModel.todo.value?.notification
                }

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.todo.value!!.notification = calendar.time

                todoNotificationTimeTextView.text = "$hourOfDay:$minute"

                todoNotificationClearButton?.visible()
            }

            timePickerFragment?.show(fragmentManager, "timePicker")
        }

        todoNotificationClearButton?.setOnClickListener {
            viewModel.todo.value?.notification = null
            todoNotificationDateTextView.text = ""
            todoNotificationTimeTextView.text = ""
            it.invisible()
        }
    }

    override fun onPause() {
        super.onPause()

        datePickerFragment?.dismiss()
        timePickerFragment?.dismiss()
    }

}