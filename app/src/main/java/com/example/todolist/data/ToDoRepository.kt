package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun insertTodo(todo: ToDo)

    suspend fun deleteTodo(todo: ToDo)

    suspend fun getTodoByID(id: Int): ToDo? // nullable, make sure app doesn't crash

    fun getTodos(): Flow<List<ToDo>>
}