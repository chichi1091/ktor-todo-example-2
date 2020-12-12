package com.todo.example.interfaces.controller

import com.todo.example.domain.Todo
import com.todo.example.interfaces.model.NewTodo
import com.todo.example.usecase.TodoUseCase
import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.inject

class TodoController(private val useCase: TodoUseCase) {
    suspend fun getAllTodos(): List<Todo> = useCase.getAllTodos()
    suspend fun getTodo(id: Int): Todo? = useCase.getTodo(id)
    suspend fun addTodo(newTodo: NewTodo): Todo = useCase.addTodo(newTodo)
    suspend fun updateTodo(newTodo: NewTodo): Todo? = useCase.updateTodo(newTodo)
    suspend fun deleteTodo(id: Int): Boolean = useCase.deleteTodo(id)
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
                val newTodo = call.receive<NewTodo>()
                call.respond(HttpStatusCode.Created, controller.addTodo(newTodo))
            }

            put("/{id}") {
                val todo = call.receive<NewTodo>()
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