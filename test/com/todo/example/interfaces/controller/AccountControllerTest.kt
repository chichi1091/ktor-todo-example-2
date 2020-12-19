package com.todo.example.interfaces.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.example.domain.account.Account
import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.infrastructure.module
import com.todo.example.infrastructure.repositoryimpl.MockAccountRepositoryImpl
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.usecase.AccountUseCase
import com.todo.example.usecase.impl.AccountUseCaseImpl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.util.KtorExperimentalAPI
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@KtorExperimentalAPI
class AccountControllerTest {
    private lateinit var mapper: ObjectMapper
    private lateinit var jwtConfig: JWTConfig

    @Before
    fun before() {
        mapper = jacksonObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        jwtConfig = JWTConfig()
    }

    @Test
    fun アカウントの新規作成が行える() {
        val account = Account.reconstruct(1, "test", "test@hoge.com")
        val mockModule: Module = module {
            single{ AccountController(get()) }
            single<AccountUseCase> { AccountUseCaseImpl(get()) }
            single{ MockAccountRepositoryImpl(account) as AccountRepository }
        }

        val engine: TestApplicationEngine = TestApplicationEngine().apply {
            start(wait = false)
            application.module(true, mockModule)
        }

        val newAccount =
            com.todo.example.interfaces.model.NewAccountRequest(null, "password", "test", "test@hoge.com")

        with(engine) {
            handleRequest(HttpMethod.Post, "/accounts") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(newAccount))
            }.response.apply {
                assertEquals(HttpStatusCode.Created, status())
                assertNotNull(content)
            }
            stopKoin()
        }
    }
}