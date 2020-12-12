package com.todo.example.interfaces.repository

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo

interface TodoRepository {
    suspend fun findAll(): List<Todo>
    suspend fun findById(id: Int): Todo?
    suspend fun create(todo: NewTodo): Todo
    suspend fun update(todo: NewTodo): Todo?
    suspend fun delete(id: Int): Boolean
}