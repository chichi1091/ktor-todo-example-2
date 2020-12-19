package com.todo.example.usecase

import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.usecase.dto.TodoDto

interface TodoUseCase {
    suspend fun getAllTodos(): List<Todo>
    suspend fun getTodo(id: Int): TodoDto
    suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoDto
    suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoDto
    suspend fun deleteTodo(id: Int): Boolean
}