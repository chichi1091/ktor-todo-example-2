package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo
import com.todo.example.interfaces.repository.TodoRepository

class MockTodoRepositoryImpl(
    private val todos: List<Todo>
): TodoRepository {
    override suspend fun findAll(): List<Todo> {
        return todos
    }
    override suspend fun findById(id: Int): Todo? {
        return null
    }
    override suspend fun create(todo: NewTodo): Todo {
        return Todo(3, "test3")
    }
    override suspend fun update(todo: NewTodo): Todo? {
        return null
    }
    override suspend fun delete(id: Int): Boolean {
        return true
    }
}
