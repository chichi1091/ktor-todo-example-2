package com.todo.example.web

import com.todo.example.module
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.handleRequest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class TodoControllerTest {
    private lateinit var engine: TestApplicationEngine

    @Before
    fun before() {
        engine = TestApplicationEngine().apply {
            start(wait = false)
            application.module()
        }
    }

    @Test
    fun タスク一覧を呼び出すと200が変えること() {
        with(engine) {
            handleRequest(HttpMethod.Get, "/todos").response.apply {
                assertEquals(HttpStatusCode.OK, status())
            }
        }
    }
}