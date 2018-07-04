package ru.aleksandrtrushchinskii.surfproject.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.create.CreateViewModel
import ru.aleksandrtrushchinskii.surfproject.ui.search.SearchViewModel


class ViewModelFactory(
        private val auth: Authentication,
        private val todoRepository: TodoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        CreateViewModel::class.java -> CreateViewModel(auth, todoRepository) as T
        SearchViewModel::class.java -> SearchViewModel(todoRepository) as T
        else -> throw RuntimeException("Unknown view model: $modelClass")
    }

}