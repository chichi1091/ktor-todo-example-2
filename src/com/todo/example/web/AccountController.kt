package com.todo.example.web

import com.todo.example.JWTConfig
import com.todo.example.model.Login
import com.todo.example.model.NewAccount
import com.todo.example.service.AccountService
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
fun Route.accounts() {
    val accountService by inject<AccountService>()
    val jwtConfig = JWTConfig()

    route("accounts") {
        post("/") {
            val newAccount = call.receive<NewAccount>()
            val account = accountService.createAccount(newAccount)
            val token = jwtConfig.createToken(account.id)
            val response = AuthResponse(token)
            call.respond(HttpStatusCode.Created, response)
        }

        post("/auth") {
            val login = call.receive<Login>()
            val account = accountService.authentication(login.email, login.password)
            if(account == null) call.respond(HttpStatusCode.Forbidden)
            else {
                val token = jwtConfig.createToken(account.id)
                val response = AuthResponse(token)
                call.respond(HttpStatusCode.OK, response)
            }
        }
    }
}