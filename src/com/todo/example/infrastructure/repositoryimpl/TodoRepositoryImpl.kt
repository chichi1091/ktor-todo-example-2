package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.account.AccountId
import com.todo.example.domain.todo.Status
import com.todo.example.domain.todo.Todo
import com.todo.example.domain.todo.TodoId
import com.todo.example.infrastructure.dao.Todos
import com.todo.example.infrastructure.framework.DatabaseFactory.dbQuery
import com.todo.example.interfaces.model.NewTodoModel
import com.todo.example.interfaces.repository.TodoRepository
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.*

@KtorExperimentalAPI
class TodoRepositoryImpl: TodoRepository {
    override suspend fun findAll(): List<Todo> = dbQuery {
        Todos.selectAll().map { convertTodo(it) }
    }

    override suspend fun findById(id: Int): Todo? = dbQuery {
        Todos.select {
            (Todos.id eq id)
        }.mapNotNull { convertTodo(it) }
            .singleOrNull()
    }

    override suspend fun create(todoModel: NewTodoModel): Todo {
        var key = 0
        dbQuery {
            key = (Todos.insert {
                it[task] = todoModel.task
            } get Todos.id)
        }
        return findById(key)!!
    }

    override suspend fun update(todoModel: NewTodoModel): Todo? {
        val id = todoModel.id
        return if (id == null) {
            create(todoModel)
        } else {
            dbQuery {
                Todos.update({ Todos.id eq id }) {
                    it[task] = todoModel.task
                }
            }
            findById(id)
        }
    }

    override suspend fun delete(id: Int): Boolean {
        return dbQuery {
            Todos.deleteWhere { Todos.id eq id } > 0
        }.also {
            false
        }
    }

    private fun convertTodo(row: ResultRow): Todo =
        Todo(
            todoId = TodoId(row[Todos.id]),
            task = row[Todos.task],
            status = Status.valueOf(row[Todos.status]),
            personId = AccountId(row[Todos.personId]),
        )
}