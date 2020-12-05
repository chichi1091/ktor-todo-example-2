package com.todo.example.service

import com.todo.example.model.Account
import com.todo.example.model.NewAccount

class MockAccountServiceImpl(
    private val account: Account
): AccountService {
    override suspend fun getAccount(id: Int): Account? {
        return account
    }

    override suspend fun createAccount(account: NewAccount): Account {
        return this.account
    }

    override suspend fun authentication(email: String, password: String): Account? {
        return account
    }
}