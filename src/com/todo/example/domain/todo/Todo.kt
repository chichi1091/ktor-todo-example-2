package com.todo.example.domain.todo

import com.todo.example.domain.account.AccountId

class Todo private constructor(
    val todoId: TodoId?,
    var task: String,
    var status: Status,
    val personId: AccountId,
) {
    companion object {
        fun reconstruct(id: Int, task: String, status: String, personId: Int): Todo =
            Todo(TodoId(id), task, Status.valueOf(status), AccountId(personId))

        fun createTodo(task: String, personId: Int): Todo =
            Todo(null, task, Status.INCOMPLETE, AccountId(personId))
    }

    fun updateTodo(task: String, status: Status) {
        this.task = task
        this.status = status
    }
}
