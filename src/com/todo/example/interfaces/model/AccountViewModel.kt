package com.todo.example.interfaces.model

data class Login (val email: String, val password: String,)

data class NewAccount (val id: Int?, val password: String, val name: String, val email: String,)
