package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.account.Account
import com.todo.example.infrastructure.dao.Accounts
import com.todo.example.infrastructure.framework.DatabaseFactory.dbQuery
import com.todo.example.interfaces.repository.AccountRepository
import io.ktor.util.KtorExperimentalAPI
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.security.MessageDigest

@KtorExperimentalAPI
class AccountRepositoryImpl: AccountRepository {
    override suspend fun findByAccountId(id: Int): Account? = dbQuery {
        Accounts.select {
            (Accounts.id eq id)
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    override suspend fun findByEmail(email: String): Account? = dbQuery  {
        Accounts.select {
            (Accounts.email eq email)
        }.mapNotNull { convertAccount(it) }
            .singleOrNull()
    }

    override suspend fun createAccount(account: Account, passwd: String): Account {
        var key = 0
        dbQuery {
            key = (Accounts.insert {
                it[password] = createHash(passwd)
                it[name] = account.name
                it[email] = account.email
            } get Accounts.id)
        }
        return findByAccountId(key)!!
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
        Account.reconstruct(row[Accounts.id], row[Accounts.name], row[Accounts.email],)
}