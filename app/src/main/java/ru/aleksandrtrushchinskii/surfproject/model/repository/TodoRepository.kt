package ru.aleksandrtrushchinskii.surfproject.model.repository

import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.aleksandrtrushchinskii.surfproject.model.database.TodoDatabase
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


class TodoRepository(private val database: TodoDatabase) {

    var loadListener: ListenerRegistration? = null


    fun create(todo: Todo) = launch {
        database.create(todo)
    }

    fun update(todo: Todo) = launch {
        database.update(todo)
    }

    fun load(query: String = "", callback: (List<Todo>) -> Unit) = launch {
        loadListener = database.load(query, callback)
    }

    fun get(id: String, callback: (Todo) -> Unit) = async {
        database.get(id, callback)
    }

    fun delete(todo: Todo) = launch {
        database.delete(todo.id)
    }

}