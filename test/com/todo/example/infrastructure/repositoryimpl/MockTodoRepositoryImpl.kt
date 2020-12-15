package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.account.AccountId
import com.todo.example.domain.todo.Status
import com.todo.example.domain.todo.Todo
import com.todo.example.domain.todo.TodoId
import com.todo.example.interfaces.model.NewTodoModel
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
    override suspend fun create(todoModel: NewTodoModel): Todo {
        return Todo(TodoId(3), "test3", Status.INCOMPLETE, AccountId(1))
    }
    override suspend fun update(todoModel: NewTodoModel): Todo? {
        return null
    }
    override suspend fun delete(id: Int): Boolean {
        return true
    }
}
