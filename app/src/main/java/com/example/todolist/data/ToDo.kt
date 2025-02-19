package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ToDo(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)
