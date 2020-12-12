package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.infrastructure.dao.Todos
import com.todo.example.infrastructure.framework.DatabaseFactory
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.insert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@KtorExperimentalAPI
class TodoRepositoryImplTest {
    private val serviceImpl: TodoRepositoryImpl =
        TodoRepositoryImpl()

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