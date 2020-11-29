package com.todo.example.service

import com.todo.example.factory.DatabaseFactory
import com.todo.example.model.Todos
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.insert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@KtorExperimentalAPI
class TodoServiceImplTest {
    private val serviceImpl: TodoServiceImpl = TodoServiceImpl()

    @Before
    fun setup() {
        DatabaseFactory.init()
    }

    @Test
    fun タスクが5件取得できること() {
        runBlocking {
            repeat(5) {
                DatabaseFactory.dbQuery {
                    Todos.insert { it[task] = "test$it" }
                }
            }

            val todos = serviceImpl.getAllTodos()
            Assert.assertEquals(todos.size, 5)
        }
    }
}