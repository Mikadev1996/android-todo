package com.example.todolist.data

import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImplementation(
    private val dao: ToDoDao
): ToDoRepository {
    override suspend fun insertTodo(todo: ToDo) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo: ToDo) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoByID(id: Int): ToDo? {
        return dao.getTodoByID(id)
    }

    override fun getTodos(): Flow<List<ToDo>> {
        return dao.getTodos()
    }
}