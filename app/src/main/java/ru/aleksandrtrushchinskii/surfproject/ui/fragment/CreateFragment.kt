package ru.aleksandrtrushchinskii.surfproject.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.create_fragment.*
import ru.aleksandrtrushchinskii.surfproject.R
import ru.aleksandrtrushchinskii.surfproject.common.tools.configurateDate
import ru.aleksandrtrushchinskii.surfproject.common.tools.inflateBinding
import ru.aleksandrtrushchinskii.surfproject.databinding.CreateFragmentBinding
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.ViewModelFactory
import javax.inject.Inject
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel


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

        configurateDate(fragmentManager!!, viewModel,
                createTodoNotificationDateTextView,
                createTodoNotificationTimeTextView,
                createTodoNotificationDateButton,
                createTodoNotificationTimeButton,
                createTodoNotificationClearButton
        )

        LoadingState.stop()
    }

}