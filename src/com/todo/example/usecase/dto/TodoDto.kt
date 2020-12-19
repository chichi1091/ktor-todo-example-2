package com.todo.example.usecase.dto

import com.todo.example.domain.todo.Status
import com.todo.example.domain.todo.TodoId

data class TodoDto(
    val id: TodoId,
    val task: String,
    val status: Status,
    val personName: String,
)