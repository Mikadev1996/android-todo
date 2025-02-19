package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) // What happens if todo exists when already exists, insert and update in one.
    suspend fun insertTodo(todo: ToDo)

    @Delete
    suspend fun deleteTodo(todo: ToDo)

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getTodoByID(id: Int): ToDo? // nullable, make sure app doesn't crash

    @Query("SELECT * FROM todo")
    fun getTodos(): Flow<List<ToDo>>
}