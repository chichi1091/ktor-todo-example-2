package com.todo.example.interfaces.model

data class LoginRequest(val email: String, val password: String,)

data class NewAccountRequest(val id: Int?, val password: String, val name: String, val email: String,)
