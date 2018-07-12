package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.read_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.service.Alarm
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_ID_KEY
import ru.aleksandrtrushchinskii.surfproject.common.tools.fullDateFormat
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.common.tools.mainActivity
import ru.aleksandrtrushchinskii.surfproject.databinding.ReadFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import javax.inject.Inject


class ReadFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var alarm: Alarm

    private lateinit var binding: ReadFragmentBinding
    private lateinit var viewModel: TodoViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(TodoViewModel::class.java)

        val todoId = arguments?.getString(TODO_ID_KEY)
        if (todoId != null) {
            viewModel.setTodo(todoId)
        }

        binding = container.inflateBinding(R.layout.read_fragment)
        binding.setLifecycleOwner(this)
        binding.todoViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readEditTodo.setOnClickListener {
            mainActivity?.navigation?.startFragment(EditFragment())
        }

        readDeleteTodo.setOnClickListener {
            viewModel.delete {
                if (viewModel.todo.value?.notification?.time != null &&
                        viewModel.todo.value?.notification?.time!! > System.currentTimeMillis()) {
                    alarm.cancel(viewModel.todo.value!!)
                }
                mainActivity?.navigation?.startFragment(SearchFragment())
            }
        }

        viewModel.todo.observe(this, android.arch.lifecycle.Observer {
            if (it?.notification != null) {
                readNotification.text = fullDateFormat.format(it.notification)
            }
        })
    }

}