package com.todo.example.interfaces.controller

import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.interfaces.model.AuthResponse
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountController(private val useCase: AccountUseCase) {
    private val jwtConfig = JWTConfig()

    suspend fun createAccount(newAccount: NewAccountRequest): AuthResponse {
        val accountId = useCase.createAccount(newAccount)
        val token = jwtConfig.createToken(accountId.raw)
        return AuthResponse(token)
    }

    suspend fun authentication(loginRequest: LoginRequest): AuthResponse? {
        val accountId = useCase.authentication(loginRequest)
        return if(accountId == null) null
        else {
            val token = jwtConfig.createToken(accountId.raw)
            AuthResponse(token)
        }
    }
}
