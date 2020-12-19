package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.todo.Status
import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.repository.TodoRepository

class MockTodoRepositoryImpl(
    private val todos: List<Todo>
): TodoRepository {
    override suspend fun findAll(): List<Todo> = todos

    override suspend fun findById(id: Int): Todo? = null

    override suspend fun create(todo: Todo): Todo =
        Todo.reconstruct(3, "test3", Status.INCOMPLETE.toString(), 1)

    override suspend fun update(todo: Todo): Todo =
        Todo.reconstruct(3, "test3", Status.INCOMPLETE.toString(), 1)

    override suspend fun delete(id: Int): Boolean = true
}
