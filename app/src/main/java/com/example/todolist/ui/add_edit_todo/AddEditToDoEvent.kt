package com.example.todolist.ui.add_edit_todo

sealed class AddEditToDoEvent {
    data class OnTitleChange(val title: String): AddEditToDoEvent()
    data class OnDescriptionChange(val description: String): AddEditToDoEvent()
    object OnSaveToDoClick: AddEditToDoEvent()
}