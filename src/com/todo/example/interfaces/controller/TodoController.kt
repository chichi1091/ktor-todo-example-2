package com.todo.example.interfaces.controller

import com.todo.example.interfaces.model.NewTodoRequest
import com.todo.example.interfaces.model.TodoResponse
import com.todo.example.usecase.TodoUseCase

class TodoController(private val useCase: TodoUseCase) {
    suspend fun getAllTodos(): List<TodoResponse> = useCase.getAllTodos()
    suspend fun getTodo(id: Int): TodoResponse? = useCase.getTodo(id)
    suspend fun addTodo(newTodoRequest: NewTodoRequest, accountId: Int): TodoResponse =
        useCase.addTodo(newTodoRequest, accountId)
    suspend fun updateTodo(newTodoRequest: NewTodoRequest): TodoResponse? = useCase.updateTodo(newTodoRequest)
    suspend fun deleteTodo(id: Int): Boolean = useCase.deleteTodo(id)
}
