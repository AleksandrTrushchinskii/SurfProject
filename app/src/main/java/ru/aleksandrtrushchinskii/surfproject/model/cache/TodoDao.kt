package ru.aleksandrtrushchinskii.surfproject.model.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


@Dao
interface TodoDao {

    @Query("SELECT * FROM ${Todo.TABLE_NAME}")
    fun getAll(): List<Todo>

    @Insert
    fun insert(todo: Todo)

    @Insert
    fun insertAll(todos: List<Todo>)

}