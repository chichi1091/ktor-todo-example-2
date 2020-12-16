package com.todo.example.interfaces.controller

import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.interfaces.model.AuthViewModel
import com.todo.example.interfaces.model.Login
import com.todo.example.interfaces.model.NewAccountModel
import com.todo.example.usecase.AccountUseCase
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

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

@KtorExperimentalAPI
fun Route.accounts() {
    route("accounts") {
        val controller: AccountController by inject()

        post("/") {
            val newAccount = call.receive<NewAccountModel>()
            call.respond(HttpStatusCode.Created, controller.createAccount(newAccount))
        }

        post("/auth") {
            val login = call.receive<Login>()
            val result = controller.authentication(login)
            if(result == null) call.respond(HttpStatusCode.Forbidden)
            else call.respond(HttpStatusCode.OK, result)
        }
    }
}