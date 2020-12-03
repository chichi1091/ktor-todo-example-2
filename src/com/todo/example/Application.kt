package com.todo.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.SerializationFeature
import com.todo.example.factory.DatabaseFactory
import com.todo.example.model.AuthUser
import com.todo.example.service.serviceModule
import com.todo.example.web.accounts
import com.todo.example.web.todos
import com.typesafe.config.ConfigFactory
import io.ktor.application.*
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.jwt
import io.ktor.config.ApplicationConfig
import io.ktor.config.HoconApplicationConfig
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
    val appConfig = HoconApplicationConfig(ConfigFactory.load())

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
        }
    }

    install(Koin) {
        if(testing && mockModule != null) modules(mockModule)
        else modules(serviceModule)
    }

    val jwtConfig = JWTConfig()
    install(Authentication) {
        jwt {
            realm = javaClass.packageName
            verifier(
                JWT.require(jwtConfig.algorithm)
                    .withAudience(jwtConfig.audience)
                    .withIssuer(jwtConfig.issuer)
                    .build()
            )

            validate {
                it.payload.getClaim(jwtConfig.userId).let { claim ->
                    if (!claim.isNull) {
                        AuthUser(claim.asInt())
                    } else {
                        null
                    }
                }
            }
        }
    }

    DatabaseFactory.init()

    install(Routing) {
        todos()
        accounts()
    }
}

class JWTConfig(var appConfig: ApplicationConfig) {
    constructor() {
        appConfig = HoconApplicationConfig(ConfigFactory.load())
    }

    val algorithm: Algorithm
    get() {
        return Algorithm.HMAC256(appConfig.property("jwt.algorithm").getString())
    }

    val issuer: String
    get() {
        return appConfig.property("jwt.issuer").getString()
    }

    val audience: String
    get() {
        return appConfig.property("jwt.audience").getString()
    }

    val userId: String
    get() {
        return appConfig.property("jwt.userId").getString()
    }
}
