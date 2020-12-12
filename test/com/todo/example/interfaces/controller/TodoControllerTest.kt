package com.todo.example.interfaces.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.example.domain.Todo
import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.infrastructure.module
import com.todo.example.infrastructure.repositoryimpl.MockTodoRepositoryImpl
import com.todo.example.interfaces.repository.TodoRepository
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.handleRequest
import io.ktor.util.KtorExperimentalAPI
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals

@KtorExperimentalAPI
class TodoControllerTest {
    private lateinit var mapper: ObjectMapper
    private lateinit var jwtConfig: JWTConfig

    @Before
    fun before() {
        mapper = jacksonObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
        jwtConfig = JWTConfig()
    }

    private fun TestApplicationRequest.addJwtHeader(id: Int) =
        addHeader(HttpHeaders.Authorization, "Bearer ${getToken(id)}")

    private fun getToken(id: Int) = jwtConfig.createToken(id)

    @Test
    fun タスク一覧を呼び出すと200が変えること() {
        val todos = listOf(
            Todo(1, "test1"),
            Todo(2, "test2")
        )
        val mockModule: Module = module {
            single{ MockTodoRepositoryImpl(todos) as TodoRepository }
        }

        val engine: TestApplicationEngine = TestApplicationEngine().apply {
            start(wait = false)
            application.module(true, mockModule)
        }

        with(engine) {
            handleRequest(HttpMethod.Get, "/todos"){
                addJwtHeader(1)
            }.response.apply {
                assertEquals(HttpStatusCode.OK, status())
                assertEquals(mapper.writeValueAsString(todos), content)
            }
        }
    }
}
