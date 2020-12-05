package com.todo.example.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.example.model.Account
import com.todo.example.model.NewAccount
import com.todo.example.module
import com.todo.example.service.AccountService
import com.todo.example.service.MockAccountServiceImpl
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals

class AccountControllerTest {
    private lateinit var mapper: ObjectMapper

    @Before
    fun before() {
        mapper = jacksonObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    @Test
    fun アカウントの新規作成が行える() {
        val account = Account(1, "password", "test", "test@hoge.com")
        val mockModule: Module = module {
            single{ MockAccountServiceImpl(account) as AccountService }
        }

        val engine: TestApplicationEngine = TestApplicationEngine().apply {
            start(wait = false)
            application.module(true, mockModule)
        }

        val newAccount = NewAccount(null, "password", "test", "test@hoge.com")

        with(engine) {
            handleRequest(HttpMethod.Post, "/accounts") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(newAccount))
            }.response.apply {
                assertEquals(HttpStatusCode.Created, status())
                assertEquals(mapper.writeValueAsString(account), content)
            }
        }
    }
}