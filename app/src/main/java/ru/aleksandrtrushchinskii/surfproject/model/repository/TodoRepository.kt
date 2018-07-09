package ru.aleksandrtrushchinskii.surfproject.model.repository

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


class TodoRepository(private val database: TodoDatabase) {

    fun create(todo: Todo) = launch {
        database.create(todo)
    }

    fun update(todo: Todo) = launch {
        database.update(todo)
    }

    fun load() = async {
        database.load()
    }

    fun get(id: String) = async {
        database.get(id)
    }

    fun delete(todo: Todo) = launch {
        database.delete(todo.id)
    }

    fun search(query: String) = async {
//        cache.search(query)
    }

}