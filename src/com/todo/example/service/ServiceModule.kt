package com.todo.example.service

import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

val serviceModule = module {
    singleBy<TodoService, TodoServiceImpl>()
    singleBy<AccountService, AccountServiceImpl>()
}