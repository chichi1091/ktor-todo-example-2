package com.todo.example.interfaces.repository

import com.todo.example.domain.account.Account

interface AccountRepository {
    suspend fun getAccount(id: Int): Account?
    suspend fun createAccount(account: Account, passwd: String): Account
    suspend fun authentication(email: String, password: String): Account?
}