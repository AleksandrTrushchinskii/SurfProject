package ru.aleksandrtrushchinskii.surfproject.model.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo.Companion.TABLE_NAME
import java.util.*


@Entity(tableName = TABLE_NAME)
data class Todo(
        @PrimaryKey
        @get:Exclude
        var id: String = "",

        var title: String = "",

        var description: String = "",

        @ColumnInfo(name = "user_id")
        var userId: String = "",

        @ServerTimestamp
        @ColumnInfo(name = "created_date")
        var createdDate: Date? = null
){

        companion object {
            const val TABLE_NAME = "todos"
        }

}