package com.example.todolist.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDo
import com.example.todolist.data.ToDoRepository
import com.example.todolist.util.Routes
import com.example.todolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val repository: ToDoRepository
) : ViewModel() {

    val todos = repository.getTodos()
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deletedToDo: ToDo? = null

    fun onEvent(event: ToDoListEvent) {
        when (event) {
            is ToDoListEvent.OnToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?toDoId=${event.toDo.id}"))
            }
            is ToDoListEvent.OnAddToDoClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is ToDoListEvent.OnUndoDeleteClick -> {
                deletedToDo?.let { toDo ->
                    viewModelScope.launch {
                        repository.insertTodo(toDo)
                    }
                }
            }
            is ToDoListEvent.OnDeleteToDoClick -> {
                viewModelScope.launch {
                    deletedToDo = event.toDo
                    repository.deleteTodo(event.toDo)
                    sendUiEvent(UiEvent.ShowSnackBar(
                        message = "ToDo Deleted",
                        action = "Undo"
                    ))
                }
            }
            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repository.insertTodo(
                        event.toDo.copy(
                            isCompleted = event.isCompleted
                        )
                    )
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

// 50.35
// https://www.youtube.com/watch?v=A7CGcFjQQtQ
// implement to do list screen, go over above file, UiEvent and ToDoListEvent