package ru.aleksandrtrushchinskii.surfproject.model.database

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import ru.aleksandrtrushchinskii.surfproject.common.service.Authentication
import ru.aleksandrtrushchinskii.surfproject.common.tools.logDebug
import ru.aleksandrtrushchinskii.surfproject.common.tools.logError
import ru.aleksandrtrushchinskii.surfproject.common.tools.toTodo
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo
import java.util.*
import kotlin.coroutines.experimental.suspendCoroutine


class TodoDatabase(
        firestore: FirebaseFirestore,
        private val auth: Authentication
) {

    private val db = firestore.collection("todos")


    fun create(todo: Todo) = db.document().addSnapshotListener { documentSnapshot, e ->
        if (e != null) {
            logError("Todo creating was failed : $e")
            return@addSnapshotListener
        }

        documentSnapshot ?: return@addSnapshotListener

        if (!documentSnapshot.exists()) {
            documentSnapshot.reference.set(todo)
            logDebug("Todo was created")
        }
    }

    fun update(todo: Todo) = db.document(todo.id).addSnapshotListener { documentSnapshot, e ->
        if (e != null) {
            logError("Todo updating was failed : $e")
            return@addSnapshotListener
        }

        documentSnapshot ?: return@addSnapshotListener

        if (documentSnapshot.exists()) {
            documentSnapshot.reference.update(todo.toMap())
            logDebug("Todo was updated")
        }
    }

    fun load(query: String = "", callback: (List<Todo>) -> Unit) = db.whereEqualTo("userId", auth.uid)
            .orderBy("createdDate", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, e ->
                if (e != null) {
                    logError("Todo loading was failed : $e")
                    return@addSnapshotListener
                }

                querySnapshot ?: return@addSnapshotListener

                if (!querySnapshot.isEmpty) {
                    var todos = mutableListOf<Todo>()

                    for (document in querySnapshot.documents) {
                        todos.add(document.toTodo())
                    }

                    todos = todos.filter {
                        it.title.contains(query, true) || it.description.contains(query, true)
                    }.toMutableList()

                    logDebug("Loaded : $todos")
                    callback(todos)
                }
            }


    fun get(id: String, callback: (Todo) -> Unit) = db.document(id).addSnapshotListener { documentSnapshot, e ->
        if (e != null) {
            logError("Todo getting was failed : $e")
            return@addSnapshotListener
        }

        documentSnapshot ?: return@addSnapshotListener

        if (documentSnapshot.exists()) {
            val todo = documentSnapshot.toTodo()

            logDebug("Todo was get : $todo")

            callback(todo)
        }
    }

    fun delete(id: String) = db.document(id).addSnapshotListener { documentSnapshot, e ->
        if (e != null) {
            logError("Todo deleting was failed : $e")
            return@addSnapshotListener
        }

        documentSnapshot ?: return@addSnapshotListener

        if (documentSnapshot.exists()) {
            documentSnapshot.reference.delete()
            logDebug("Todo was delete")
        }
    }

    suspend fun getSoonTodos() = suspendCoroutine<List<Todo>> { continuation ->
        db.whereGreaterThan("notification", Date(System.currentTimeMillis()))
                .get(Source.CACHE)
                .addOnSuccessListener {
                    val todos = mutableListOf<Todo>()

                    for (document in it.documents) {
                        todos.add(document.toTodo())
                    }

                    logDebug("Todos to notify : $todos")

                    continuation.resume(todos)
                }.addOnFailureListener {
                    logError("Getting soon todos was failed")
                }
    }

}