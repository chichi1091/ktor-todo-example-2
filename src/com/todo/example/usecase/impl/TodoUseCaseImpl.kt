package com.todo.example.usecase.impl

import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.interfaces.model.TodoResponse
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.TodoUseCase

class TodoUseCaseImpl(
    private val todoRepository: TodoRepository,
    private val accountRepository: AccountRepository,
): TodoUseCase {
    override suspend fun getAllTodos(): List<TodoResponse> {
        val todos = todoRepository.findAll()
        return todos.map {
            val account = accountRepository.findByAccountId(it.personId.raw)
                ?: throw NullPointerException()
            TodoResponse(it.todoId?.raw, it.task, it.status, account.name)
        }
    }

    override suspend fun getTodo(id: Int): TodoResponse? {
        val todo = todoRepository.findById(id)
        return if(todo == null) null
        else {
            val account = accountRepository.findByAccountId(todo.personId.raw)
                ?: throw NullPointerException()
            TodoResponse(todo.todoId?.raw, todo.task, todo.status, account.name)
        }
    }

    override suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoResponse {
        val todo = Todo.createTodo(newTodoRequest.task, accountId)
        val newTodo = todoRepository.create(todo)
        return run {
            val account = accountRepository.findByAccountId(newTodo.personId.raw)
                ?: throw NullPointerException()
            TodoResponse(newTodo.todoId?.raw, newTodo.task, newTodo.status, account.name)
        }
    }

    override suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoResponse? {
        val todo = newTodoRequest.id?.let { todoRepository.findById(it) }
            ?: throw IllegalArgumentException("タスクが存在しません")
        todo.updateTodo(newTodoRequest.task, newTodoRequest.status)

        val newTodo = todoRepository.update(todo)
        return if(newTodo == null) null
        else {
            val account = accountRepository.findByAccountId(newTodo.personId.raw)
                ?: throw NullPointerException()
            TodoResponse(newTodo.todoId?.raw, newTodo.task, newTodo.status, account.name)
        }
    }

    override suspend fun deleteTodo(id: Int): Boolean = todoRepository.delete(id)
}