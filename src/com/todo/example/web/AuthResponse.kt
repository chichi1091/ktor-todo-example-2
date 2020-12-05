package com.todo.example.web

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponse(
    @JsonProperty("bearer_token")
    val token: String
)