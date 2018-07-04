package ru.aleksandrtrushchinskii.surfproject.ui.search

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository


class SearchViewModel(private val repository: TodoRepository) : ViewModel() {

    fun load() = launch(UI) {
        TodoAdapter.setData(repository.load().await())
    }

}