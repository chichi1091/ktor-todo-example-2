package com.todo.example.usecase

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo

interface TodoUseCase {
    suspend fun getAllTodos(): List<Todo>
    suspend fun getTodo(id: Int): Todo?
    suspend fun addTodo(newTodo: NewTodo): Todo
    suspend fun updateTodo(newTodo: NewTodo): Todo?
    suspend fun deleteTodo(id: Int): Boolean
}