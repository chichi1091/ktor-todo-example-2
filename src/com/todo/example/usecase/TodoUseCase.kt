package com.todo.example.usecase

import com.todo.example.interfaces.model.NewTodoModel
import com.todo.example.interfaces.model.TodoModel

interface TodoUseCase {
    suspend fun getAllTodos(): List<TodoModel>
    suspend fun getTodo(id: Int): TodoModel?
    suspend fun addTodo(newTodoModel: NewTodoModel, accountId: Int): TodoModel
    suspend fun updateTodo(newTodoModel: NewTodoModel): TodoModel?
    suspend fun deleteTodo(id: Int): Boolean
}