package com.todo.example.usecase.impl

import com.todo.example.domain.Account
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccount
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountUseCaseImpl(private val accountRepository: AccountRepository): AccountUseCase {

    override suspend fun createAccount(newAccount: NewAccount): Account =
        accountRepository.createAccount(newAccount)

    override suspend fun authentication(login: Login): Account? =
        accountRepository.authentication(login.email, login.password)
}