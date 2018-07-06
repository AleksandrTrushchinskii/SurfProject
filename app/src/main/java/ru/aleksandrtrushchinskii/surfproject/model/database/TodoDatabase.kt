package ru.aleksandrtrushchinskii.surfproject.model.database

import com.google.firebase.firestore.FirebaseFirestore
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.common.tools.logError
import ru.aleksandrtrushchinskii.surfproject.common.tools.toTodo
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import kotlin.coroutines.experimental.suspendCoroutine


class TodoDatabase(firestore: FirebaseFirestore) {

    private val db = firestore.collection("todos")


    suspend fun create(todo: Todo) = suspendCoroutine<String> { continuation ->
        db.add(todo).addOnSuccessListener {
            logDebug("Todo was created with id ${it.id} : $todo")
            continuation.resume(it.id)
        }.addOnFailureListener {
            logError(it.toString())
        }
    }

    suspend fun load() = suspendCoroutine<List<Todo>> { continuation ->
        db.orderBy("createdDate").get().addOnSuccessListener {
            val todos = arrayListOf<Todo>()

            it.documents.forEach {
                todos.add(it.toTodo())
            }

            logDebug("Loaded todos : $todos")

            continuation.resume(todos)
        }.addOnFailureListener {
            logError("Todos loading was failed")
        }
    }

    suspend fun get(id: String) = suspendCoroutine<Todo> { continuation ->
        db.document(id).get().addOnSuccessListener {
            val todo = it.toTodo()

            logDebug("Loaded todo : $todo")

            continuation.resume(todo)
        }.addOnFailureListener {
            logError("Todos loading was failed")
        }
    }

}