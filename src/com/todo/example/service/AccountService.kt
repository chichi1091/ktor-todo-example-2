package com.todo.example.service

import com.todo.example.model.Account
import com.todo.example.model.GoogleAccount
import com.todo.example.model.NewAccount

interface AccountService {
    suspend fun getAccount(id: Int): Account?
    suspend fun createAccount(account: NewAccount): Account
    suspend fun createOAuthAccount(account: GoogleAccount): Account
    suspend fun authentication(email: String, password: String): Account?
}