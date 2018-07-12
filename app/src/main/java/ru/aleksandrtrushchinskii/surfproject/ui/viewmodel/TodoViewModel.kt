package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository


class TodoViewModel(
        private val auth: Authentication,
        private val repository: TodoRepository
) : ViewModel() {

    val todo = MutableLiveData<Todo>().apply { value = Todo() }


    fun setTodo(id: String) = launch(UI) {
        repository.get(id) { todo.value = it }
    }

    fun create(callback: () -> Unit) {
        launch(UI) {
            repository.create(todo.value!!.apply { userId = auth.uid }).join()

            callback()

            todo.value = Todo()
        }
    }

    fun edit(callback: () -> Unit) {
        launch(UI) {
            repository.update(todo.value!!).join()

            callback()
        }
    }

    fun delete(callback: () -> Unit) {
        launch(UI) {
            repository.delete(todo.value!!).join()

            callback()
        }
    }

}