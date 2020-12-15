package com.todo.example.interfaces.model

import com.todo.example.domain.todo.Status

data class TodoModel (val id: Int?, val task: String, val status: Status, val personName: String,)

data class NewTodoModel (val id: Int?, val task: String,)