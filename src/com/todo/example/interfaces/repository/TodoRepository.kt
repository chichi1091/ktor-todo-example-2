package com.todo.example.interfaces.repository

import com.todo.example.domain.todo.Todo

interface TodoRepository {
    suspend fun findAll(): List<Todo>
    suspend fun findById(id: Int): Todo?
    suspend fun create(todo: Todo): Todo
    suspend fun update(todo: Todo): Todo
    suspend fun delete(id: Int): Boolean
}