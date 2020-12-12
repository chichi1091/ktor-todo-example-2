package com.todo.example.infrastructure.dao

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Accounts: Table() {
    val id: Column<Int> = Accounts.integer("id").autoIncrement().primaryKey()
    val password: Column<String> = varchar("password", 4000)
    val name: Column<String> = varchar("name", 4000)
    val email: Column<String> = varchar("email", 4000)
}
