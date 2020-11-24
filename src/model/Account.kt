package com.todo.exmaple.model

import com.todo.exmaple.model.Todos.autoIncrement
import com.todo.exmaple.model.Todos.primaryKey
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Accounts: Table() {
    val id: Column<Int> = Todos.integer("id").autoIncrement().primaryKey()
    val password: Column<String> = varchar("password", 4000)
    val googleId: Column<String> = varchar("googleId", 4000)
    val name: Column<String> = varchar("name", 4000)
    val email: Column<String> = varchar("email", 4000)
}

data class Account (
    val id: Int,
    val password: String?,
    val googleId: String?,
    val name: String,
    val email: String
)