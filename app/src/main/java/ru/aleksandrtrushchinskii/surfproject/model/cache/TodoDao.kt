package ru.aleksandrtrushchinskii.surfproject.model.cache

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import ru.aleksandrtrushchinskii.surfproject.model.entity.Todo


@Dao
interface TodoDao {

    @Insert
    fun insert(todo: Todo)

    @Insert
    fun insertAll(todos: List<Todo>)

    @Update
    fun update(todo: Todo)

    @Query("SELECT * FROM ${Todo.TABLE_NAME} WHERE id = :id")
    fun get(id: String): Todo

    @Query("SELECT * FROM ${Todo.TABLE_NAME}")
    fun getAll(): List<Todo>

}