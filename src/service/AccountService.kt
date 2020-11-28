package com.todo.exmaple.service

import com.todo.exmaple.factory.DatabaseFactory.dbQuery
import com.todo.exmaple.model.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.fillParameters
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.util.*

class AccountService {
    suspend fun getAccount(id: String): Account? = dbQuery {
        Todos.select {
            (Accounts.id eq id)
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    suspend fun createAccount(account: NewAccount): Account {
        val conn = TransactionManager.current().connection
        val query = """
insert into accounts
(id, password, name, email)
values
(?, crypt(?, gen_salt('md5'), ?, ?)
"""
        val key = UUID.randomUUID().toString()
        val parameter = listOf(
            Pair(VarCharColumnType(), key),
            Pair(VarCharColumnType(), account.password),
            Pair(VarCharColumnType(), account.name),
            Pair(VarCharColumnType(), account.email),
        )
        val statement = conn.prepareStatement(query)
        statement.fillParameters(parameter)
        statement.executeUpdate()

        return getAccount(key)!!
    }

    suspend fun createOAuthAccount(account: GoogleAccount): Account {
        var key = UUID.randomUUID().toString()
        dbQuery {
            key = (Accounts.insert {
                it[id] = key
                it[googleId] = account.googleId
                it[name] = account.name
                it[email] = account.email
            } get Accounts.id)
        }
        return getAccount(key)!!
    }

    suspend fun isAuthentication(email: String, password: String): Boolean {
        val conn = TransactionManager.current().connection
        val query = """
select
    id
from
    accounts
where
    email = ? and
    password = crypt(?, gen_salt('md5')
"""
        val parameter = listOf(
            Pair(VarCharColumnType(), email),
            Pair(VarCharColumnType(), password),
        )
        val statement = conn.prepareStatement(query)
        statement.fillParameters(parameter)
        val result = statement.executeQuery()
        result.last()
        val numberOfRow = result.row

        return numberOfRow >= 1
    }

    private fun convertAccount(row: ResultRow): Account =
        Account(
            id = row[Accounts.id],
            password = row[Accounts.password],
            name = row[Accounts.name],
            googleId = row[Accounts.googleId],
            email = row[Accounts.email],
        )
}