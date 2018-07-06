package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation


class CreateViewModel(
        private val auth: Authentication,
        private val repository: TodoRepository
) : ViewModel() {

    val todo = MutableLiveData<Todo>().apply {
        value = Todo()
    }


    fun create() = launch(UI) {
        LoadingState.start()

        repository.create(todo.value!!.apply { userId = auth.uid }).join()

        todo.value = Todo()

        Navigation.finishCurrentFragment()

        LoadingState.stop()
    }

}