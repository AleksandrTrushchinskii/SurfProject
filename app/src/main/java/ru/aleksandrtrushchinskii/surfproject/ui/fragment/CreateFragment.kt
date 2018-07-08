package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.create_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.databinding.CreateFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.DatePickerFragment
import javax.inject.Inject
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.TimePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import java.util.*


class CreateFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: CreateFragmentBinding
    private lateinit var viewModel: TodoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)


        binding = container.inflateBinding(R.layout.create_fragment)
        binding.setLifecycleOwner(this)
        binding.todoViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.todo.value?.notification != null) {
            val c = Calendar.getInstance()

            c.time = viewModel.todo.value?.notification

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)

            if (year != null && month != null && month != day) {
                createTodoNotificationDateTextView.text = "$day:$month:$year"
            }

            if (hour != null && minute != null) {
                createTodoNotificationTimeTextView.text = "$hour:$minute"
            }
        }

        createTodoNotificationDateButton.setOnClickListener {
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

                createTodoNotificationDateTextView.text = "$dayOfMonth:$month:$year"
            }
            newFragment.show(fragmentManager, "datePicker")
        }

        createTodoNotificationTimeButton.setOnClickListener {
            val newFragment = TimePickerFragment()

            newFragment.listener = { hourOfDay, minute ->
                val calendar = Calendar.getInstance()

                if (viewModel.todo.value?.notification != null) {
                    calendar.time = viewModel.todo.value?.notification
                }

                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)

                viewModel.todo.value!!.notification = calendar.time

                createTodoNotificationTimeTextView.text = "$hourOfDay:$minute"
            }

            newFragment.show(fragmentManager, "timePicker")
        }

        LoadingState.stop()
    }

}