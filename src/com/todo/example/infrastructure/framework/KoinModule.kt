package com.todo.example.infrastructure.framework

import com.todo.example.infrastructure.repositoryimpl.AccountRepositoryImpl
import com.todo.example.infrastructure.repositoryimpl.TodoRepositoryImpl
import com.todo.example.interfaces.controller.AccountController
import com.todo.example.interfaces.controller.TodoController
import com.todo.example.interfaces.presenter.AccountPresenter
import com.todo.example.interfaces.presenter.TodoPresenter
import com.todo.example.interfaces.repository.AccountRepository
import com.todo.example.interfaces.repository.TodoRepository
import com.todo.example.usecase.AccountUseCase
import com.todo.example.usecase.TodoUseCase
import com.todo.example.usecase.impl.AccountUseCaseImpl
import com.todo.example.usecase.impl.TodoUseCaseImpl
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module
import org.koin.experimental.builder.singleBy

@KtorExperimentalAPI
val koinModule = module {
    // Controller
    single { AccountController(get(), get()) }
    single { TodoController(get(), get()) }

    // Presenter
    single { AccountPresenter() }
    single { TodoPresenter() }

    // UseCase
    single<AccountUseCase> { AccountUseCaseImpl(get()) }
    single<TodoUseCase> { TodoUseCaseImpl(get(), get()) }

    // Repository
    singleBy<TodoRepository, TodoRepositoryImpl>()
    singleBy<AccountRepository, AccountRepositoryImpl>()
}