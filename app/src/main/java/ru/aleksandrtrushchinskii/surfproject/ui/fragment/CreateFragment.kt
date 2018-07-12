package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.create_edit_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Alarm
import ru.aleksandrtrushchinskii.surfproject.common.tools.*
import ru.aleksandrtrushchinskii.surfproject.databinding.CreateEditFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.DatePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.TimePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import javax.inject.Inject


class CreateFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var alarm: Alarm

    private lateinit var binding: CreateEditFragmentBinding
    private lateinit var viewModel: TodoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)

        binding = container.inflateBinding(R.layout.create_edit_fragment)

        with(binding) {
            setLifecycleOwner(this@CreateFragment)
            todoViewModel = viewModel
            action = "create"
        }

        if (savedInstanceState == null) viewModel.todo.value = Todo()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurateDate()

        actionTodoButton.setOnClickListener {
            viewModel.create {
                if (viewModel.todo.value?.notification?.time != null &&
                        viewModel.todo.value?.notification?.time!! > System.currentTimeMillis()) {
                    alarm.resetAlarm(viewModel.todo.value!!)
                }
                mainActivity?.navigation?.startFragment(SearchFragment())
            }
        }
    }

    private fun configurateDate() {
        if (viewModel.todo.value?.notification == null) todoNotificationClearButton.invisible()

        todoNotificationDateButton.setOnClickListener {
            DatePickerFragment().show(fragmentManager, "datePicker")
        }

        todoNotificationTimeButton.setOnClickListener {
            TimePickerFragment().show(fragmentManager, "timePicker")
        }

        todoNotificationClearButton.setOnClickListener {
            viewModel.todo.value?.notification = null
            todoNotificationDateTextView.text = ""
            todoNotificationTimeTextView.text = ""
            it.invisible()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(DATE_TEXT, todoNotificationDateTextView.text.toString())
        outState.putString(TIME_TEXT, todoNotificationTimeTextView.text.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        todoNotificationDateTextView.text = savedInstanceState?.getString(DATE_TEXT)
        todoNotificationTimeTextView.text = savedInstanceState?.getString(TIME_TEXT)
    }

}