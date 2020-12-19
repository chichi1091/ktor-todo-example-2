package com.todo.example.interfaces.controller

import com.todo.example.interfaces.model.AuthResponse
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest
import com.todo.example.interfaces.presenter.AccountPresenter
import com.todo.example.usecase.AccountUseCase
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
class AccountController(
    private val useCase: AccountUseCase,
    private val presenter: AccountPresenter,
) {

    suspend fun createAccount(newAccount: NewAccountRequest): AuthResponse {
        val account = useCase.createAccount(newAccount)
        return presenter.toAuthResponse(account) ?: throw Exception("アカウントの作成に失敗しました")
    }

    suspend fun authentication(loginRequest: LoginRequest): AuthResponse? {
        val account = useCase.authentication(loginRequest)
        return presenter.toAuthResponse(account)
    }
}
