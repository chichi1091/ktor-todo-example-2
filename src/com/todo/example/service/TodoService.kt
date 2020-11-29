package com.todo.example.service

import com.todo.example.model.NewTodo
import com.todo.example.model.Todo

interface TodoService {
    suspend fun getAllTodos(): List<Todo>
    suspend fun getTodo(id: Int): Todo?
    suspend fun addTodo(todo: NewTodo): Todo
    suspend fun updateTodo(todo: NewTodo): Todo?
    suspend fun deleteTodo(id: Int): Boolean
}