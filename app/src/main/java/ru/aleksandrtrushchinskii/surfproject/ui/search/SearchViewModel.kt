package ru.aleksandrtrushchinskii.surfproject.ui.search

import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateFragment


class SearchViewModel(private val repository: TodoRepository) : ViewModel() {

    fun load() = launch(UI) {
        TodoAdapter.setData(repository.load().await())
        LoadingState.stop()
    }

    fun startCreating(){
        Navigation.startFragment(CreateFragment::class.java.simpleName)
    }

}