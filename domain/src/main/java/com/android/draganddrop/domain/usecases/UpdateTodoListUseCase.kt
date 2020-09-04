package com.android.draganddrop.domain.usecases

import com.android.draganddrop.domain.models.TodoDomain
import com.android.draganddrop.domain.repositories.TodoRepository
import com.android.draganddrop.domain.usecases.base.CompleteableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class UpdateTodoListUseCase @Inject constructor(private val todoRepository: TodoRepository) :
    CompleteableUseCase<Unit,List<TodoDomain>>() {


    override fun buildFlowUseCase(params: List<TodoDomain>?): Completable {
        return todoRepository.updateTodoList(params!!)
    }

}