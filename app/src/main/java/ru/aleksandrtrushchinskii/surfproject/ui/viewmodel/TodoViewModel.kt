package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.service.Internet
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository


class TodoViewModel(
        private val auth: Authentication,
        private val repository: TodoRepository,
        private val internet: Internet
) : ViewModel() {

    val todo = MutableLiveData<Todo>().apply { value = Todo() }


    fun setTodo(id: String) = launch(UI) {
        todo.value = repository.get(id).await()

    }

    fun create(callback: () -> Unit) {
        internet.ifAvailable {
            launch(UI) {
                repository.create(todo.value!!.apply { userId = auth.uid }).join()

                todo.value = Todo()

                callback()
            }
        }
    }

    fun edit(callback: () -> Unit) {
        internet.ifAvailable {
            launch(UI) {
                repository.update(todo.value!!).join()

                callback()
            }
        }
    }

    fun delete(callback: () -> Unit) {
        internet.ifAvailable {
            launch(UI) {
                repository.delete(todo.value!!).join()

                callback()
            }
        }
    }

}