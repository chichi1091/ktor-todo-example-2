package com.todo.example.usecase

import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.interfaces.model.TodoResponse

interface TodoUseCase {
    suspend fun getAllTodos(): List<TodoResponse>
    suspend fun getTodo(id: Int): TodoResponse?
    suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoResponse
    suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoResponse?
    suspend fun deleteTodo(id: Int): Boolean
}