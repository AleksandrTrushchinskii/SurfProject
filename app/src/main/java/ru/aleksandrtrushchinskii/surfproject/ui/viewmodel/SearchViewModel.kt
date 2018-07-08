package ru.aleksandrtrushchinskii.surfproject.ui.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.common.tools.ACTION_KEY
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.adapter.TodoAdapter
import ru.aleksandrtrushchinskii.surfproject.ui.component.LoadingState
import ru.aleksandrtrushchinskii.surfproject.ui.component.Navigation
import ru.aleksandrtrushchinskii.surfproject.ui.fragment.CreateEditFragment


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
        Navigation.startFragment(
                CreateEditFragment::class.java.simpleName,
                Bundle().apply { putString(ACTION_KEY, "create") }
        )
    }

}