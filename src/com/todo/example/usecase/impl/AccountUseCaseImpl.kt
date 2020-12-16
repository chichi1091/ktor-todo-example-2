package com.todo.example.usecase.impl

import com.todo.example.domain.account.Account
import com.todo.example.domain.account.AccountId
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccountModel
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountUseCaseImpl(private val accountRepository: AccountRepository): AccountUseCase {

    override suspend fun createAccount(newAccount: NewAccountModel): AccountId {
        val account = Account.createAccount(newAccount.name, newAccount.email)
        return accountRepository.createAccount(account, newAccount.password).accountId!!
    }

    override suspend fun authentication(login: Login): AccountId? =
        accountRepository.authentication(login.email, login.password)?.accountId
}