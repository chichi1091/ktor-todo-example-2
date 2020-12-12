package com.todo.example.infrastructure

import com.fasterxml.jackson.databind.SerializationFeature
import com.todo.example.infrastructure.framework.AuthUser
import com.todo.example.infrastructure.framework.DatabaseFactory
import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.infrastructure.framework.koinModule
import com.todo.example.interfaces.controller.accounts
import com.todo.example.interfaces.controller.todos
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin

fun main(args: Array<String>): Unit = io.ktor.server.tomcat.EngineMain.main(args)

@KtorExperimentalAPI
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false, mockModule: Module? = null) {
    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Koin) {
        if(testing && mockModule != null) modules(mockModule)
        else modules(koinModule)
    }

    val jwtConfig = JWTConfig()
    install(Authentication) {
        jwt {
            realm = jwtConfig.realm
            verifier(jwtConfig.makeJwtVerifier())
            validate {credential ->
                if (credential.payload.audience.contains(jwtConfig.audience)) {
                    val principal = JWTPrincipal(credential.payload)
                    AuthUser.toAuthUser(principal)
                    principal
                } else null
            }
        }
    }

    DatabaseFactory.init()

    install(Routing) {
        todos()
        accounts()
    }
}
