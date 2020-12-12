package com.todo.example.infrastructure.framework

import io.ktor.auth.Principal
import io.ktor.auth.jwt.JWTPrincipal

data class AuthUser (val id: Int): Principal {
    companion object {
        fun toAuthUser(principal: JWTPrincipal) = AuthUser(
            principal.payload.getClaim("user_id")?.asInt() ?: throw IllegalStateException("unauthorized")
        )
    }
}
