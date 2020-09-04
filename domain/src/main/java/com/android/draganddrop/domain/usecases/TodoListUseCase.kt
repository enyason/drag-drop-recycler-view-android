package com.android.draganddrop.domain.usecases

import com.android.draganddrop.domain.models.TodoDomain
import com.android.draganddrop.domain.repositories.TodoRepository
import com.android.draganddrop.domain.usecases.base.ObservableUseCase
import io.reactivex.Maybe
import javax.inject.Inject

class TodoListUseCase @Inject constructor(private val todoRepository: TodoRepository) :
    ObservableUseCase<List<TodoDomain>, Unit>() {


    override fun buildFlowUseCase(params: Unit?): Maybe<List<TodoDomain>> {
        return todoRepository.todoList()
    }

}