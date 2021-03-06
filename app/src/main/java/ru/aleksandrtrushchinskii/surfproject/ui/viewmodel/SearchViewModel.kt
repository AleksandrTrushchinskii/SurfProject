package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository


class SearchViewModel(private val repository: TodoRepository) : ViewModel() {

    val query = MutableLiveData<String>()


    fun load(callBack: (List<Todo>) -> Unit) = launch(UI) {
        if (!query.value.isNullOrEmpty()) {
            repository.load(query.value!!, callBack)
        } else {
            repository.load(callback = callBack)
        }
    }

    fun unregister() {
        repository.loadListener?.remove()
    }

}