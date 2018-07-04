package ru.aleksandrtrushchinskii.surfproject.model.database

import com.google.firebase.firestore.FirebaseFirestore
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.common.tools.logError
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import kotlin.coroutines.experimental.suspendCoroutine


class TodoDatabase(firestore: FirebaseFirestore) {

    private val db = firestore.collection("todos")


    suspend fun create(todo: Todo) = suspendCoroutine<Unit> { continuation ->
        db.add(todo).addOnSuccessListener {
            logDebug("Todo was created : $todo")
            continuation.resume(Unit)
        }.addOnFailureListener {
            logError(it.toString())
        }
    }

    suspend fun load() = suspendCoroutine<List<Todo>> { continuation ->
        db.get().addOnSuccessListener {
            val todos = arrayListOf<Todo>()

            it.documents.forEach {
                todos.add(
                        it.toObject(Todo::class.java).apply {
                            this!!.id = it.id
                        }!!
                )
            }

            logDebug("Loaded todos : $todos")

            continuation.resume(todos)
        }.addOnFailureListener {
            logError("Todos loading was failed")
        }
    }

}