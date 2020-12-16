package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.todo.Todo
import com.todo.example.infrastructure.dao.Todos
import com.todo.example.infrastructure.framework.DatabaseFactory.dbQuery
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

    override suspend fun create(todo: Todo): Todo {
        var key = 0
        dbQuery {
            key = (Todos.insert {
                it[task] = todo.task
                it[status] = todo.status.toString()
                it[personId] = todo.personId.raw
            } get Todos.id)
        }
        return findById(key)!!
    }

    override suspend fun update(todo: Todo): Todo? {
        val id = todo.todoId?.raw
        return if (id == null) {
            create(todo)
        } else {
            dbQuery {
                Todos.update({ Todos.id eq id }) {
                    it[task] = todo.task
                    it[status] = todo.status.toString()
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
        Todo.reconstruct(row[Todos.id], row[Todos.task], row[Todos.status], row[Todos.personId],)
}