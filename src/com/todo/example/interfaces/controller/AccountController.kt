package com.todo.example.interfaces.controller

import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.interfaces.model.AuthViewModel
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccountModel
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountController(private val useCase: AccountUseCase) {
    private val jwtConfig = JWTConfig()

    suspend fun createAccount(newAccount: NewAccountModel): AuthViewModel {
        val accountId = useCase.createAccount(newAccount)
        val token = jwtConfig.createToken(accountId.raw)
        return AuthViewModel(token)
    }

    suspend fun authentication(login: Login): AuthViewModel? {
        val accountId = useCase.authentication(login)
        return if(accountId == null) null
        else {
            val token = jwtConfig.createToken(accountId.raw)
            AuthViewModel(token)
        }
    }
}
