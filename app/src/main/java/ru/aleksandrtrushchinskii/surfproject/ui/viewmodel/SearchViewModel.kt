package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.CreateFragment


class SearchViewModel(private val repository: TodoRepository) : ViewModel() {

    val query = MutableLiveData<String>()


    fun load() = launch(UI) {
        if (!query.value.isNullOrEmpty()) {
            TodoAdapter.setData(repository.search("%${query.value}%").await())
        } else {
            TodoAdapter.setData(repository.load().await())
        }
        LoadingState.stop()
    }

    fun startCreating() {
        Navigation.startFragment(CreateFragment::class.java.simpleName)
    }

}