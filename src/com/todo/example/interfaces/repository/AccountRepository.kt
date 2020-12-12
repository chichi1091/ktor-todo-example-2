package com.todo.example.interfaces.repository

import com.todo.example.domain.Account
import com.todo.example.interfaces.model.NewAccount

interface AccountRepository {
    suspend fun getAccount(id: Int): Account?
    suspend fun createAccount(account: NewAccount): Account
    suspend fun authentication(email: String, password: String): Account?
}