package com.todo.example.interfaces.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.example.domain.account.Account
import com.todo.example.domain.account.AccountId
import com.todo.example.domain.todo.Status
import com.todo.example.domain.todo.Todo
import com.todo.example.domain.todo.TodoId
import com.todo.example.infrastructure.framework.JWTConfig
import com.todo.example.infrastructure.module
import com.todo.example.infrastructure.repositoryimpl.MockAccountRepositoryImpl
import com.todo.example.infrastructure.repositoryimpl.MockTodoRepositoryImpl
import com.todo.example.interfaces.model.TodoModel
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.TodoUseCase
import com.todo.example.usecase.impl.TodoUseCaseImpl
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.handleRequest
import io.ktor.util.KtorExperimentalAPI
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin
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
            Todo(TodoId(1), "test1", Status.INCOMPLETE, AccountId(1)),
            Todo(TodoId(2), "test2", Status.COMPLETED, AccountId(1)),
        )
        val account = Account(AccountId(1), "test", "hoge@hoge.com")

        val todoModels = listOf(
            TodoModel(1, "test1", Status.INCOMPLETE, "test"),
            TodoModel(2, "test2", Status.COMPLETED, "test"),
        )

        val mockModule: Module = module {
            single { TodoController(get()) }
            single<TodoUseCase> { TodoUseCaseImpl(get(), get()) }
            single{ MockAccountRepositoryImpl(account) as AccountRepository }
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
                assertEquals(mapper.writeValueAsString(todoModels), content)
            }
            stopKoin()
        }
    }
}
