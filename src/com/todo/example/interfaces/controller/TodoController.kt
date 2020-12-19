package com.todo.example.interfaces.controller

import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.interfaces.model.TodoResponse
import com.todo.example.interfaces.model.TodosResponse
import com.todo.example.interfaces.presenter.TodoPresenter
import com.todo.example.usecase.TodoUseCase

class TodoController(
    private val useCase: TodoUseCase,
    private val presenter: TodoPresenter,
) {
    suspend fun getAllTodos(): List<TodosResponse> {
        val todos = useCase.getAllTodos()
        return presenter.toTodosResponse(todos)
    }

    suspend fun getTodo(id: Int): TodoResponse? {
        val todoDto = useCase.getTodo(id)
        return presenter.toTodoResponse(todoDto)
    }

    suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoResponse {
        val todoDto = useCase.addTodo(newTodoRequest, accountId)
        return presenter.toTodoResponse(todoDto)
    }

    suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoResponse? {
        val todoDto = useCase.updateTodo(newTodoRequest)
        return presenter.toTodoResponse(todoDto)
    }

    suspend fun deleteTodo(id: Int): Boolean = useCase.deleteTodo(id)
}
