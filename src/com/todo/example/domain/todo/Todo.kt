package com.todo.example.domain.todo

import com.todo.example.domain.account.AccountId

data class Todo (
    val todoId: TodoId,
    val task: String,
    val status: Status,
    val personId: AccountId,
)
