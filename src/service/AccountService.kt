package com.todo.exmaple.service

import com.todo.exmaple.factory.DatabaseFactory.dbQuery
import com.todo.exmaple.model.Accounts
import com.todo.exmaple.model.NewAccount
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.statements.fillParameters
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.util.*

class AccountService {
    suspend fun createAccount(account: NewAccount) {
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
    }
}