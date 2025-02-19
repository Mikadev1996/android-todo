package com.example.todolist.di

import android.app.Application
import androidx.room.Room
import com.example.todolist.data.ToDoDatabase
import com.example.todolist.data.ToDoRepository
import com.example.todolist.data.ToDoRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesToDoRepository(db: ToDoDatabase): ToDoRepository {
        return ToDoRepositoryImplementation(db.dao)
    }
}
