package com.todo.example.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.todo.example.model.Todo
import com.todo.example.module
import com.todo.example.service.MockTodoServiceImpl
import com.todo.example.service.TodoService
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import org.junit.Before
import org.junit.Test
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.assertEquals

class TodoControllerTest {
    private lateinit var mapper: ObjectMapper

    @Before
    fun before() {
        mapper = jacksonObjectMapper()
        mapper.enable(SerializationFeature.INDENT_OUTPUT)
    }

    @Test
    fun タスク一覧を呼び出すと200が変えること() {
        val todos = listOf(Todo(1, "test1"), Todo(2, "test2"))
        val mockModule: Module = module {
            single{ MockTodoServiceImpl(todos) as TodoService }
        }

        val engine: TestApplicationEngine = TestApplicationEngine().apply {
            start(wait = false)
            application.module(true, mockModule)
        }

        with(engine) {
            handleRequest(HttpMethod.Get, "/todos").response.apply {
                assertEquals(HttpStatusCode.OK, status())
                assertEquals(mapper.writeValueAsString(todos), content)
            }
        }
    }
}
