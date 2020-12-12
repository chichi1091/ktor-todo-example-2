package com.todo.example.infrastructure.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Todos: Table() {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val task: Column<String> = varchar("task", 4000)
}
