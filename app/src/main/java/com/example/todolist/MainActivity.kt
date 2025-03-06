package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todolist.ui.add_edit_todo.AddEditToDoScreen
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.ui.todo_list.ToDoListScreen
import com.example.todolist.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TODO_LIST) {
                    composable(Routes.TODO_LIST) {
                        ToDoListScreen(onNavigate = {
                            navController.navigate(it.route)
                        })
                    }
                    composable(
                        route = Routes.ADD_EDIT_TODO + "?toDoId={toDoId}",
                        arguments = listOf(
                            navArgument(name = "toDoId") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        AddEditToDoScreen(onPopBackStack = {
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}