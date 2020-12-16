package com.todo.example.usecase

import com.todo.example.domain.account.AccountId
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccountModel

interface AccountUseCase {
    suspend fun createAccount(newAccount: NewAccountModel): AccountId
    suspend fun authentication(login: Login): AccountId?
}