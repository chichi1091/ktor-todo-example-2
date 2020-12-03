package com.todo.example.service

import com.todo.example.factory.DatabaseFactory
import com.todo.example.model.Accounts
import com.todo.example.model.NewAccount
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.insert
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@KtorExperimentalAPI
class AccountServiceImplTest {
    private val serviceImpl: AccountServiceImpl = AccountServiceImpl()

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
            Assert.assertEquals(sut?.id, id)
            Assert.assertEquals(sut?.password, serviceImpl.createHash(testPassword))
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

            val account = serviceImpl.createAccount(
                NewAccount(id = null, email = email, password = password, name = name)
            )
            Assert.assertEquals(account.id, 1)
            Assert.assertEquals(account.password, serviceImpl.createHash(password))
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
            Assert.assertEquals(sut?.id, id)
            Assert.assertEquals(sut?.password, serviceImpl.createHash(testPassword))
            Assert.assertEquals(sut?.email, testEmail)
            Assert.assertEquals(sut?.name, testName)
        }
    }
}