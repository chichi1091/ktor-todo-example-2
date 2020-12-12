package com.todo.example.usecase

import com.todo.example.domain.Account
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccount

interface AccountUseCase {
    suspend fun createAccount(newAccount: NewAccount): Account
    suspend fun authentication(login: Login): Account?
}