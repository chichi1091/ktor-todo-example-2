package com.todo.example.infrastructure.repositoryimpl

import com.todo.example.domain.account.Account
import com.todo.example.domain.account.AccountId
import com.todo.example.infrastructure.dao.Accounts
import com.todo.example.infrastructure.framework.DatabaseFactory
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.insert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@KtorExperimentalAPI
class AccountRepositoryImplTest {
    private val serviceImpl: AccountRepositoryImpl =
        AccountRepositoryImpl()

    @Before
    fun setup() {
        DatabaseFactory.init()
    }

    @Test
    fun アカウントが検索できる() {
        runBlocking {

            val testEmail = "test1@hoge.com"
            val testPassword = "password"
            val testName = "てすとたろう"

            var id = 0
            DatabaseFactory.dbQuery {
                id = Accounts.insert {
                    it[email] = testEmail
                    it[password] = serviceImpl.createHash(testPassword)
                    it[name] = testName
                } get Accounts.id
            }

            val sut = serviceImpl.getAccount(id)

            Assert.assertNotEquals(sut, null)
            Assert.assertEquals(sut?.accountId, AccountId(id))
            Assert.assertEquals(sut?.email, testEmail)
            Assert.assertEquals(sut?.name, testName)
        }

    }

    @Test
    fun アカウントが登録できる() {
        runBlocking {

            val email = "test2@hoge.com"
            val password = "password"
            val name = "てすとたろう"

            val account = serviceImpl.createAccount(Account.createAccount(name, email), password)
            Assert.assertEquals(account.accountId, AccountId(1))
            Assert.assertEquals(account.email, email)
            Assert.assertEquals(account.name, name)
        }
    }

    @Test
    fun アカウント認証ができる() {
        runBlocking {

            val testEmail = "test3@hoge.com"
            val testPassword = "password"
            val testName = "てすとたろう"

            var id = 0
            DatabaseFactory.dbQuery {
                id = Accounts.insert {
                    it[email] = testEmail
                    it[password] = serviceImpl.createHash(testPassword)
                    it[name] = testName
                } get Accounts.id
            }

            val sut = serviceImpl.authentication(testEmail, testPassword)

            Assert.assertNotEquals(sut, null)
            Assert.assertEquals(sut?.accountId, AccountId(id))
            Assert.assertEquals(sut?.email, testEmail)
            Assert.assertEquals(sut?.name, testName)
        }
    }
}