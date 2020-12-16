package com.todo.example.usecase.impl

import com.todo.example.domain.todo.Todo
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
            TodoModel(it.todoId?.raw, it.task, it.status, account.name)
        }
    }

    override suspend fun getTodo(id: Int): TodoModel? {
        val todo = todoRepository.findById(id)
        return if(todo == null) null
        else {
            val account = accountRepository.getAccount(todo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(todo.todoId?.raw, todo.task, todo.status, account.name)
        }
    }

    override suspend fun addTodo(newTodoModel: NewTodoModel, accountId: Int): TodoModel {
        val todo = Todo.createTodo(newTodoModel.task, accountId)
        val newTodo = todoRepository.create(todo)
        return run {
            val account = accountRepository.getAccount(newTodo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(newTodo.todoId?.raw, newTodo.task, newTodo.status, account.name)
        }
    }

    override suspend fun updateTodo(newTodoModel: NewTodoModel): TodoModel? {
        val todo = newTodoModel.id?.let { todoRepository.findById(it) }
            ?: throw IllegalArgumentException("タスクが存在しません")
        todo.updateTodo(newTodoModel.task, newTodoModel.status)

        val newTodo = todoRepository.update(todo)
        return if(newTodo == null) null
        else {
            val account = accountRepository.getAccount(newTodo.personId.raw)
                ?: throw NullPointerException()
            TodoModel(newTodo.todoId?.raw, newTodo.task, newTodo.status, account.name)
        }
    }

    override suspend fun deleteTodo(id: Int): Boolean = todoRepository.delete(id)
}