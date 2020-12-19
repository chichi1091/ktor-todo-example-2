package com.todo.example.usecase

import com.todo.example.domain.account.Account
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest

interface AccountUseCase {
    suspend fun createAccount(newAccount: NewAccountRequest): Account
    suspend fun authentication(loginRequest: LoginRequest): Account?
}