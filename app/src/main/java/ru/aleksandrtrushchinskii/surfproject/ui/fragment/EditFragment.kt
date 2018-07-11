package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.create_edit_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.*
import ru.aleksandrtrushchinskii.surfproject.databinding.CreateEditFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.DatePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.component.picker.TimePickerFragment
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import java.util.*
import javax.inject.Inject


class EditFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: CreateEditFragmentBinding
    private lateinit var viewModel: TodoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)

        binding = container.inflateBinding(R.layout.create_edit_fragment)
        binding.setLifecycleOwner(this)
        binding.todoViewModel = viewModel
        binding.action = "edit"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurateDate()

        actionTodoButton.setOnClickListener {
            viewModel.edit { mainActivity?.navigation?.startFragment(SearchFragment()) }
        }
    }

    private fun configurateDate() {
        if (viewModel.todo.value?.notification != null) {
            val c = Calendar.getInstance().apply { time = viewModel.todo.value?.notification }

            todoNotificationDateTextView.text = dateFormat.format(c.time)
            todoNotificationTimeTextView.text = timeFormat.format(c.time)
        } else {
            todoNotificationClearButton?.invisible()
        }

        todoNotificationDateButton?.setOnClickListener {
            DatePickerFragment().show(fragmentManager, "datePicker")
        }

        todoNotificationTimeButton?.setOnClickListener {
            TimePickerFragment().show(fragmentManager, "timePicker")
        }

        todoNotificationClearButton?.setOnClickListener {
            viewModel.todo.value?.notification = null
            todoNotificationDateTextView.text = ""
            todoNotificationTimeTextView.text = ""
            it.invisible()
        }
    }

}