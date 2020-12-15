package com.todo.example.interfaces.repository

import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.model.NewTodoModel

interface TodoRepository {
    suspend fun findAll(): List<Todo>
    suspend fun findById(id: Int): Todo?
    suspend fun create(todoModel: NewTodoModel): Todo
    suspend fun update(todoModel: NewTodoModel): Todo?
    suspend fun delete(id: Int): Boolean
}