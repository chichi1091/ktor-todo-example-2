package com.todo.example.service

import com.todo.example.model.NewTodo
import com.todo.example.model.Todo

class MockTodoServiceImpl(
    private val todos: List<Todo>
): TodoService {
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
