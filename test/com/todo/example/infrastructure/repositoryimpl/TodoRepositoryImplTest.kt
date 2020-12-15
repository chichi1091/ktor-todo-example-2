package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.todo.Status
import com.todo.example.infrastructure.dao.Accounts
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
                DatabaseFactory.dbQuery {
                    Accounts.insert {
                        it[name] = "test"
                        it[password] = "passwd"
                        it[email] = "hoge@hoge.com"
                    }

                    repeat(5) {
                        Todos.insert {
                            it[task] = "test$it"
                            it[status] = Status.INCOMPLETE.toString()
                            it[personId] = 1
                        }
                    }
                }

            val todos = serviceImpl.findAll()
            Assert.assertEquals(todos.size, 5)
        }
    }
}