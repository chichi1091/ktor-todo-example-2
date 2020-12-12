package com.todo.example.usecase.impl

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.TodoUseCase

class TodoUseCaseImpl(private val repository: TodoRepository): TodoUseCase {
    override suspend fun getAllTodos(): List<Todo> = repository.findAll()

    override suspend fun getTodo(id: Int): Todo? = repository.findById(id)

    override suspend fun addTodo(newTodo: NewTodo): Todo = repository.create(newTodo)

    override suspend fun updateTodo(newTodo: NewTodo): Todo? = repository.update(newTodo)

    override suspend fun deleteTodo(id: Int): Boolean = repository.delete(id)
}