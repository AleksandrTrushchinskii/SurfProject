package ru.aleksandrtrushchinskii.surfproject.model.repository

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.cache.AppCache
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


class TodoRepository(private val database: TodoDatabase, appCache: AppCache) {

    private val cache = appCache.todoDao()


    fun create(todo: Todo) = launch {
        val id = database.create(todo)

        val newTodo = database.get(id)

        launch { cache.insert(newTodo) }.join()
    }

    fun load() = async {
        var todos = listOf<Todo>()

        todos += cache.getAll()

        if (todos.isEmpty()) {
            todos += database.load()

            cache.insertAll(todos)
        }

        todos
    }

}