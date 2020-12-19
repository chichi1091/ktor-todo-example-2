package com.todo.example.interfaces.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @JsonProperty("bearer_token")
    val token: String
)