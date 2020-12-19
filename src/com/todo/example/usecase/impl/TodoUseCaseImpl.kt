package com.todo.example.usecase.impl

import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.TodoUseCase
import com.todo.example.usecase.dto.TodoDto

class TodoUseCaseImpl(
    private val todoRepository: TodoRepository,
    private val accountRepository: AccountRepository,
): TodoUseCase {
    override suspend fun getAllTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    override suspend fun getTodo(id: Int): TodoDto {
        val todo = todoRepository.findById(id)
            ?: throw NullPointerException()
        val account = accountRepository.findByAccountId(todo.personId.raw)
            ?: throw NullPointerException()
        return TodoDto(todo.todoId!!, todo.task, todo.status, account.name)
    }

    override suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoDto {
        val todo = Todo.createTodo(newTodoRequest.task, accountId)
        val newTodo = todoRepository.create(todo)
        return getTodo(newTodo.todoId!!.raw)
    }

    override suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoDto {
        val todo = newTodoRequest.id?.let { todoRepository.findById(it) }
            ?: throw IllegalArgumentException("タスクが存在しません")
        todo.updateTodo(newTodoRequest.task, newTodoRequest.status)

        val newTodo = todoRepository.update(todo)
        return getTodo(newTodo.todoId!!.raw)
    }

    override suspend fun deleteTodo(id: Int): Boolean = todoRepository.delete(id)
}