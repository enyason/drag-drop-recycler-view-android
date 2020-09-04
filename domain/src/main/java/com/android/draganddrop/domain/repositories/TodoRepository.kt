package com.android.draganddrop.domain.repositories

import com.android.draganddrop.domain.models.TodoDomain
import io.reactivex.Completable
import io.reactivex.Maybe

interface TodoRepository {
    fun todoList(): Maybe<List<TodoDomain>>
    fun saveTodoList(params: List<TodoDomain>): Completable
    fun updateTodoList(params: List<TodoDomain>): Completable
}