package com.todo.example.interfaces.model

import com.todo.example.domain.todo.Status

data class NewTodoRequest(val id: Int?, val task: String, val status: Status)