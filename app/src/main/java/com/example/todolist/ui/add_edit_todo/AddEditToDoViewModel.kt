package com.example.todolist.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.ToDo
import com.example.todolist.data.ToDoRepository
import com.example.todolist.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditToDoViewModel @Inject constructor(
    private val repository: ToDoRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var toDo by mutableStateOf<ToDo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val toDoId = savedStateHandle.get<Int>("todoId")!!

        if(toDoId != -1) {
            viewModelScope.launch {
                repository.getTodoByID(toDoId)?.let { toDo ->
                    title = toDo.title
                    description = toDo.description ?: ""
                    this@AddEditToDoViewModel.toDo = toDo
                }
            }
        }
    }

    fun onEvent(event: AddEditToDoEvent) {
        when (event) {
            is AddEditToDoEvent.OnTitleChange -> {
                title = event.title
            }
            is AddEditToDoEvent.OnDescriptionChange -> {
                description = event.description
            }
            is AddEditToDoEvent.OnSaveToDoClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackBar(
                            message = "Title can't be empty"
                        ))
                        return@launch
                    }
                    repository.insertTodo(
                        ToDo(
                            title = title,
                            description = description,
                            isCompleted = toDo?.isCompleted ?: false,
                            id = toDo?.id
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
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