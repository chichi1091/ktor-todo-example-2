package com.todo.example.infrastructure.framework

import com.todo.example.interfaces.controller.AccountController
import com.todo.example.interfaces.controller.TodoController
import com.todo.example.interfaces.model.LoginRequest
import com.todo.example.interfaces.model.NewAccountRequest
import com.todo.example.interfaces.model.NewTodoRequest
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

@KtorExperimentalAPI
fun Route.accounts() {
    route("accounts") {
        val controller: AccountController by inject()

        post("/") {
            val newAccount = call.receive<NewAccountRequest>()
            call.respond(HttpStatusCode.Created, controller.createAccount(newAccount))
        }

        post("/auth") {
            val login = call.receive<LoginRequest>()
            val result = controller.authentication(login)
            if(result == null) call.respond(HttpStatusCode.Forbidden)
            else call.respond(HttpStatusCode.OK, result)
        }
    }
}

@KtorExperimentalAPI
fun Route.todos() {
    val controller by inject<TodoController>()

    route("todos") {
        authenticate {
            get("/") {
                call.respond(controller.getAllTodos())
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw IllegalStateException("Must To id")
                val todo = controller.getTodo(id)
                if (todo == null) call.respond(HttpStatusCode.NotFound)
                else call.respond(todo)
            }

            post("/") {
                val newTodo = call.receive<NewTodoRequest>()
                val accountId = call.principal<AuthUser>()!!.id
                call.respond(HttpStatusCode.Created, controller.addTodo(newTodo, accountId))
            }

            put("/{id}") {
                val todo = call.receive<NewTodoRequest>()
                val updated = controller.updateTodo(todo)
                if (updated == null) call.respond(HttpStatusCode.NotFound)
                else call.respond(HttpStatusCode.OK, updated)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw IllegalStateException("Must To id");
                val removed = controller.deleteTodo(id)
                if (removed) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}