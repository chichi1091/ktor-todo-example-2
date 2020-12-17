package com.todo.example.interfaces.controller

import com.todo.example.interfaces.model.NewTodoModel
import com.todo.example.interfaces.model.TodoModel
import com.todo.example.usecase.TodoUseCase

class TodoController(private val useCase: TodoUseCase) {
    suspend fun getAllTodos(): List<TodoModel> = useCase.getAllTodos()
    suspend fun getTodo(id: Int): TodoModel? = useCase.getTodo(id)
    suspend fun addTodo(newTodoModel: NewTodoModel, accountId: Int): TodoModel =
        useCase.addTodo(newTodoModel, accountId)
    suspend fun updateTodo(newTodoModel: NewTodoModel): TodoModel? = useCase.updateTodo(newTodoModel)
    suspend fun deleteTodo(id: Int): Boolean = useCase.deleteTodo(id)
}
