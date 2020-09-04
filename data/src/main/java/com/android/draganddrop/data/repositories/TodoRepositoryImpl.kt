package com.android.draganddrop.data.repositories

import com.android.draganddrop.data.model.TodoData
import com.android.draganddrop.data.sources.DataSourceFactory
import com.android.draganddrop.domain.models.TodoDomain
import com.android.draganddrop.domain.repositories.TodoRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val dataSourceFactory: DataSourceFactory
) : TodoRepository {
    override fun todoList(): Maybe<List<TodoDomain>> {

        val remoteSource = dataSourceFactory.remote().todoList().doOnSuccess {
            dataSourceFactory.local().saveToDo(it).subscribe()
        }

        val localSource = dataSourceFactory.local().todoList()
        val combinedObservable = Maybe.concat(
            localSource,
            remoteSource
        ).filter { it.isNotEmpty() }
            .firstElement()

        return combinedObservable.map {
            it.map { data -> with(data) { TodoDomain(userId, id, title, completed) } }
        }
    }

    override fun saveTodoList(params: List<TodoDomain>): Completable {
        val list = params.map { with(it) { TodoData(userId, id, title, completed) } }

        return dataSourceFactory.local().saveToDo(list)
    }

    override fun updateTodoList(params: List<TodoDomain>): Completable {

        val displaced = params[0]
        val removed = params[1]

        val newDisplaced = TodoData(
            userId = removed.userId,
            id = displaced.id,
            completed = removed.completed,
            title = removed.title
        )
        val newRemoved = TodoData(
            userId = displaced.userId,
            id = removed.id,
            completed = displaced.completed,
            title = displaced.title
        )


        return dataSourceFactory.local().saveToDo(listOf(newDisplaced, newRemoved))
    }


}