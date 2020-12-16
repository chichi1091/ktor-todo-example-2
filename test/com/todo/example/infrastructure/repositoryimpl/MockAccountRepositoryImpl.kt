package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.account.Account
import com.todo.example.interfaces.repository.AccountRepository

class MockAccountRepositoryImpl(
    private val account: Account
): AccountRepository {
    override suspend fun getAccount(id: Int): Account? {
        return account
    }

    override suspend fun createAccount(account: Account, passwd: String): Account {
        return this.account
    }

    override suspend fun authentication(email: String, password: String): Account? {
        return account
    }
}