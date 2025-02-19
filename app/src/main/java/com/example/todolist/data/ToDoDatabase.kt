package com.example.todolist.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ToDo::class], // Tables in the database, just the todo table in this case
    version = 1 // Change when applying updates
)
abstract class ToDoDatabase: RoomDatabase() {
    abstract val dao: ToDoDao
}