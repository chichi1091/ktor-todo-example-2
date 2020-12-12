package com.todo.example.interfaces.repository

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo

interface TodoRepository {
    suspend fun getAllTodos(): List<Todo>
    suspend fun getTodo(id: Int): Todo?
    suspend fun addTodo(todo: NewTodo): Todo
    suspend fun updateTodo(todo: NewTodo): Todo?
    suspend fun deleteTodo(id: Int): Boolean
}