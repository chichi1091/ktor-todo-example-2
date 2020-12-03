package com.todo.example.service

import com.todo.example.model.Account
import com.todo.example.model.NewAccount

interface AccountService {
    suspend fun getAccount(id: Int): Account?
    suspend fun createAccount(account: NewAccount): Account
    suspend fun authentication(email: String, password: String): Account?
}