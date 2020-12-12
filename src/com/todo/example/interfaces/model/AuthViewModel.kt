package com.todo.example.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthViewModel(
    @JsonProperty("bearer_token")
    val token: String
)