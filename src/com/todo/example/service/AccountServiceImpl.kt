package com.todo.example.service

import com.todo.example.factory.DatabaseFactory.dbQuery
import com.todo.example.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.fillParameters
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.security.MessageDigest
import java.util.*

class AccountServiceImpl: AccountService {
    override suspend fun getAccount(id: Int): Account? = dbQuery {
        Accounts.select {
            (Accounts.id eq id)
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    override suspend fun createAccount(account: NewAccount): Account {
        var key = 0
        dbQuery {
            key = (Accounts.insert {
                it[password] = createHash(account.password)
                it[name] = account.name
                it[email] = account.email
            } get Accounts.id)
        }
        return getAccount(key)!!
    }

    override suspend fun createOAuthAccount(account: GoogleAccount): Account {
        var key = 0
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

    override suspend fun authentication(email: String, password: String): Account? {
        return Accounts.select {
            (Accounts.email eq email) and (Accounts.password eq createHash(password))
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    private fun createHash(value: String): String {
        return MessageDigest.getInstance("SHA-256")
            .digest(value.toByteArray())
            .joinToString(separator = "") {
                "%02x".format(it)
            }
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