package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.TODO_ID_KEY
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.databinding.ReadFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel
import javax.inject.Inject


class ReadFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

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

}