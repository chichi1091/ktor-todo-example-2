package com.todo.example.usecase.impl

import com.todo.example.interfaces.model.NewTodoModel
import com.todo.example.interfaces.model.TodoModel
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.TodoUseCase

class TodoUseCaseImpl(
    private val todoRepository: TodoRepository,
    private val accountRepository: AccountRepository,
): TodoUseCase {
    override suspend fun getAllTodos(): List<TodoModel> {
        val todos = todoRepository.findAll()
        return todos.map {
            val account = accountRepository.getAccount(it.personId.raw)
                ?: throw NullPointerException()
            TodoModel(it.todoId.raw, it.task, it.status, account.name)
        }
    }

    override suspend fun getTodo(id: Int): TodoModel? {
        val todo = todoRepository.findById(id)
        return if(todo == null) null
        else {
            val account = accountRepository.getAccount(todo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(todo.todoId.raw, todo.task, todo.status, account.name)
        }
    }

    override suspend fun addTodo(newTodoModel: NewTodoModel): TodoModel {
        val todo = todoRepository.create(newTodoModel)
        return run {
            val account = accountRepository.getAccount(todo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(todo.todoId.raw, todo.task, todo.status, account.name)
        }
    }

    override suspend fun updateTodo(newTodoModel: NewTodoModel): TodoModel? {
        val todo = todoRepository.update(newTodoModel)
        return if(todo == null) null
        else {
            val account = accountRepository.getAccount(todo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(todo.todoId.raw, todo.task, todo.status, account.name)
        }
    }

    override suspend fun deleteTodo(id: Int): Boolean = todoRepository.delete(id)
}