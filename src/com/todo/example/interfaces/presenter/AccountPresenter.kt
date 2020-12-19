package com.todo.example.interfaces.presenter

import com.todo.example.domain.account.Account
import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.interfaces.model.AuthResponse
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountPresenter {
    private val jwtConfig = JWTConfig()

    fun toAuthResponse(account: Account?): AuthResponse? {
        return if(account == null) null
        else {
            val token = jwtConfig.createToken(account.accountId!!.raw)
            AuthResponse(token)
        }
    }
}