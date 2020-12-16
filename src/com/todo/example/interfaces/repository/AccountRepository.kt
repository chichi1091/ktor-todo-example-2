package com.todo.example.interfaces.repository

import com.todo.example.domain.account.Account

interface AccountRepository {
    suspend fun findByAccountId(id: Int): Account?
    suspend fun findByEmail(email: String): Account?
    suspend fun createAccount(account: Account, passwd: String): Account
    suspend fun authentication(email: String, password: String): Account?
}