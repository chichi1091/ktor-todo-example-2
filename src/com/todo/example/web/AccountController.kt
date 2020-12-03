package com.todo.example.web

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.todo.example.JWTConfig
import com.todo.example.model.Login
import com.todo.example.model.NewAccount
import com.todo.example.service.AccountService
import com.typesafe.config.ConfigFactory
import io.ktor.application.call
import io.ktor.config.HoconApplicationConfig
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route
import org.koin.ktor.ext.inject
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

fun Route.accounts() {
    val accountService by inject<AccountService>()
    val jwtConfig = JWTConfig()

    route("accounts") {
        post("/") {
            val newAccount = call.receive<NewAccount>()
            call.respond(HttpStatusCode.Created, accountService.createAccount(newAccount))
        }

        post("/auth") {
            val login = call.receive<Login>()
            val account = accountService.authentication(login.email, login.password)
            if(account == null) call.respond(HttpStatusCode.Forbidden)
            else {
                val token = JWT.create()
                    .withAudience(jwtConfig.audience)
                    .withExpiresAt(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.UTC))) // 有効期限
                    .withClaim(jwtConfig.userId, account.id)
                    .withIssuer(jwtConfig.issuer)
                    .sign(jwtConfig.algorithm)
                call.respond(HttpStatusCode.OK, token)
            }
        }
    }
}