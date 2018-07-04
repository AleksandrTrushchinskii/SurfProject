package ru.aleksandrtrushchinskii.surfproject.model.entity

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


data class Todo(
        @get:Exclude
        var id: String = "",

        var title: String = "",

        var description: String = "",

        var userId: String = "",

        @ServerTimestamp
        var createdDate: Date? = null
)