package com.todo.example.domain.account

class Account private constructor(
    val accountId: AccountId?,
    var name: String,
    var email: String
) {
    companion object {
        fun reconstruct(id: Int, name: String, email: String): Account = Account(AccountId(id), name, email)

        fun createAccount(name: String, email: String): Account = Account(null, name, email)
    }
}
