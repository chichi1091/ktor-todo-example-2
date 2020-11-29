package com.todo.example.service

import com.todo.example.factory.DatabaseFactory.dbQuery
import com.todo.example.model.NewTodo
import com.todo.example.model.Todo
import com.todo.example.model.Todos
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.*

@KtorExperimentalAPI
class TodoServiceImpl: TodoService {
    override suspend fun getAllTodos(): List<Todo> = dbQuery {
        Todos.selectAll().map { convertTodo(it) }
    }

    override suspend fun getTodo(id: Int): Todo? = dbQuery {
        Todos.select {
            (Todos.id eq id)
        }.mapNotNull { convertTodo(it) }
            .singleOrNull()
    }

    override suspend fun addTodo(todo: NewTodo): Todo {
        var key = 0
        dbQuery {
            key = (Todos.insert {
                it[task] = todo.task
            } get Todos.id)
        }
        return getTodo(key)!!
    }

    override suspend fun updateTodo(todo: NewTodo): Todo? {
        val id = todo.id
        return if (id == null) {
            addTodo(todo)
        } else {
            dbQuery {
                Todos.update({ Todos.id eq id }) {
                    it[task] = todo.task
                }
            }
            getTodo(id)
        }
    }

    override suspend fun deleteTodo(id: Int): Boolean {
        return dbQuery {
            Todos.deleteWhere { Todos.id eq id } > 0
        }.also {
            false
        }
    }

    private fun convertTodo(row: ResultRow): Todo =
        Todo(
            id = row[Todos.id],
            task = row[Todos.task]
        )
}