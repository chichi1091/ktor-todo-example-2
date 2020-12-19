package com.todo.example.interfaces.presenter

import com.todo.example.domain.todo.Todo
import com.todo.example.interfaces.model.TodoResponse
import com.todo.example.interfaces.model.TodosResponse
import com.todo.example.usecase.dto.TodoDto

class TodoPresenter {
    fun toTodosResponse(todos: List<Todo>): List<TodosResponse> =
        todos.map {
            TodosResponse(it.todoId?.raw, it.task, it.status)
        }

    fun toTodoResponse(todoDto: TodoDto): TodoResponse =
        TodoResponse(todoDto.id.raw, todoDto.task, todoDto.status, todoDto.personName)
}