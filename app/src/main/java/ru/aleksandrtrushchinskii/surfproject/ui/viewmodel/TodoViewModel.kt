package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.service.Internet
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.EditFragment


class TodoViewModel(
        private val auth: Authentication,
        private val repository: TodoRepository,
        private val internet: Internet
) : ViewModel() {

    val todo = MutableLiveData<Todo>().apply { value = Todo() }


    fun setTodo(id: String) {
        launch(UI) {
            todo.value = repository.get(id).await()

            LoadingState.stop()
        }
    }

    fun startEditing() {
        Navigation.startFragment(EditFragment::class.java.simpleName)
    }

    fun delete() {
        internet.ifAvailable {
            launch(UI) {
                LoadingState.start()

                repository.delete(todo.value!!).join()

                Navigation.finishCurrentFragment()

                LoadingState.stop()
            }
        }
    }

    fun create() {
        internet.ifAvailable {
            launch(UI) {
                LoadingState.start()

                repository.create(todo.value!!.apply { userId = auth.uid }).join()

                todo.value = Todo()

                Navigation.finishCurrentFragment()

                LoadingState.stop()
            }
        }
    }

    fun edit() {
        internet.ifAvailable {
            launch(UI) {
                LoadingState.start()

                repository.update(todo.value!!).join()

                TodoAdapter.clear()

                Navigation.finishCurrentFragment()
            }
        }
    }

}