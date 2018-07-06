package ru.aleksandrtrushchinskii.surfproject.ui.component

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.model.repository.TodoRepository
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.CreateViewModel
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.SearchViewModel
import ru.aleksandrtrushchinskii.surfproject.ui.viewmodel.TodoViewModel


class ViewModelFactory(
        private val auth: Authentication,
        private val todoRepository: TodoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        CreateViewModel::class.java -> CreateViewModel(auth, todoRepository) as T
        SearchViewModel::class.java -> SearchViewModel(todoRepository) as T
        TodoViewModel::class.java -> TodoViewModel(todoRepository) as T
        else -> throw RuntimeException("Unknown view model: $modelClass")
    }

}