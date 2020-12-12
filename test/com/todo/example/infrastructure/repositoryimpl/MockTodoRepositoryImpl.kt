package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo
import com.todo.example.interfaces.repository.TodoRepository

class MockTodoRepositoryImpl(
    private val todos: List<Todo>
): TodoRepository {
    override suspend fun getAllTodos(): List<Todo> {
        return todos
    }
    override suspend fun getTodo(id: Int): Todo? {
        return null
    }
    override suspend fun addTodo(todo: NewTodo): Todo {
        return Todo(3, "test3")
    }
    override suspend fun updateTodo(todo: NewTodo): Todo? {
        return null
    }
    override suspend fun deleteTodo(id: Int): Boolean {
        return true
    }
}
