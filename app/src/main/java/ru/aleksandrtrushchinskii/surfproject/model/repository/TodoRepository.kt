package ru.aleksandrtrushchinskii.surfproject.model.repository

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.cache.AppCache
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


class TodoRepository(private val database: TodoDatabase, appCache: AppCache) {

    private val cache = appCache.todoDao()


    fun create(todo: Todo) = launch {
        //todo implement creating todo in cache
        database.create(todo)
    }

    fun load() = async {
        var todos = listOf<Todo>()

        todos += cache.getAll()

        if (todos.isEmpty()) {
            todos += database.load()
            launch {
                cache.insertAll(todos)
            }
        }

        todos
    }

}