package com.todo.example.service

import com.todo.example.factory.DatabaseFactory.dbQuery
import com.todo.example.model.Account
import com.todo.example.model.Accounts
import com.todo.example.model.NewAccount
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.security.MessageDigest

@KtorExperimentalAPI
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
                it[password] = createHash(account.password!!)
                it[name] = account.name
                it[email] = account.email
            } get Accounts.id)
        }
        return getAccount(key)!!
    }

    override suspend fun authentication(email: String, password: String): Account? = dbQuery {
        Accounts.select {
            (Accounts.email eq email) and (Accounts.password eq createHash(password))
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    internal fun createHash(value: String): String {
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
            email = row[Accounts.email],
        )
}