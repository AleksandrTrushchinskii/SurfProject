package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.service.Internet
import ru.aleksandrtrushchinskii.surfproject.common.tools.ACTION_KEY
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.CreateEditFragment


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
        Navigation.startFragment(
                CreateEditFragment::class.java.simpleName,
                Bundle().apply { putString(ACTION_KEY, "edit") })
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

    fun action(action: String) {
        if (action == "create") {
            internet.ifAvailable {
                launch(UI) {
                    LoadingState.start()

                    repository.create(todo.value!!.apply { userId = auth.uid }).join()

                    todo.value = Todo()

                    Navigation.finishCurrentFragment()

                    LoadingState.stop()
                }
            }
        } else {
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

}