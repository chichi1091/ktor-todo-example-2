package com.todo.example.interfaces.controller

import com.todo.example.interfaces.model.NewTodo
import com.todo.example.interfaces.repository.TodoRepository
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

@KtorExperimentalAPI
fun Route.todos() {
    val todoService by inject<TodoRepository>()

    route("todos") {
        authenticate {
            get("/") {
                call.respond(todoService.getAllTodos())
            }

            get("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw IllegalStateException("Must To id")
                val widget = todoService.getTodo(id)
                if (widget == null) call.respond(HttpStatusCode.NotFound)
                else call.respond(widget)
            }

            post("/") {
                val newTodo = call.receive<NewTodo>()
                call.respond(HttpStatusCode.Created, todoService.addTodo(newTodo))
            }

            put("/{id}") {
                val todo = call.receive<NewTodo>()
                val updated = todoService.updateTodo(todo)
                if (updated == null) call.respond(HttpStatusCode.NotFound)
                else call.respond(HttpStatusCode.OK, updated)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toInt()
                    ?: throw IllegalStateException("Must To id");
                val removed = todoService.deleteTodo(id)
                if (removed) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}