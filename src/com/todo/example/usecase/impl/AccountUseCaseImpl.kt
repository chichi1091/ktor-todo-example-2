package com.todo.example.usecase.impl

import com.todo.example.domain.account.Account
import com.todo.example.domain.account.AccountId
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountUseCaseImpl(private val accountRepository: AccountRepository): AccountUseCase {

    override suspend fun createAccount(newAccount: NewAccountRequest): AccountId {
        val duplicate = accountRepository.findByEmail(newAccount.email)
        if(duplicate != null) throw IllegalArgumentException("既に登録済みのメールアドレスです")

        val account = Account.createAccount(newAccount.name, newAccount.email)
        return accountRepository.createAccount(account, newAccount.password).accountId!!
    }

    override suspend fun authentication(loginRequest: LoginRequest): AccountId? =
        accountRepository.authentication(loginRequest.email, loginRequest.password)?.accountId
}