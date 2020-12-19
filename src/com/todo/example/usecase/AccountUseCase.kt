package com.todo.example.usecase

import com.todo.example.domain.account.AccountId
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest

interface AccountUseCase {
    suspend fun createAccount(newAccount: NewAccountRequest): AccountId
    suspend fun authentication(loginRequest: LoginRequest): AccountId?
}