package com.example.todolist.ui.todo_list

import com.example.todolist.data.ToDo

sealed class ToDoListEvent {
    object OnAddToDoClick: ToDoListEvent()
    data class OnToDoClick(val toDo: ToDo): ToDoListEvent()
    data class OnDeleteToDoClick(val toDo: ToDo): ToDoListEvent()
    object OnUndoDeleteClick: ToDoListEvent()
    data class OnDoneChange(val toDo: ToDo, val isCompleted: Boolean): ToDoListEvent()
}