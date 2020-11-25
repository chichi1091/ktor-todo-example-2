package com.todo.exmaple.model

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Accounts: Table() {
    val id: Column<String> = varchar("id", 50)
    val password: Column<String> = varchar("password", 4000)
    val googleId: Column<String> = varchar("googleId", 4000)
    val name: Column<String> = varchar("name", 4000)
    val email: Column<String> = varchar("email", 4000)
}

data class Account (
    val id: String,
    val password: String?,
    val googleId: String?,
    val name: String,
    val email: String
)

data class NewAccount (
    val id: String?,
    val password: String,
    val name: String,
    val email: String
)

data class GoogleAccount (
    val id: String?,
    val googleId: String,
    val name: String,
    val email: String
)
