package com.todo.example.model

import io.ktor.auth.Principal
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Accounts: Table() {
    val id: Column<Int> = Accounts.integer("id").autoIncrement().primaryKey()
    val password: Column<String> = varchar("password", 4000)
    val name: Column<String> = varchar("name", 4000)
    val email: Column<String> = varchar("email", 4000)
}

data class Account (
    val id: Int,
    val password: String,
    val name: String,
    val email: String
)

data class Login (
    val email: String,
    val password: String
)

data class AuthUser (
    val id: Int
): Principal

data class NewAccount (
    val id: Int?,
    val password: String,
    val name: String,
    val email: String,
)
