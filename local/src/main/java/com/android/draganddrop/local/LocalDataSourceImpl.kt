package com.android.draganddrop.local

import com.android.draganddrop.data.model.TodoData
import com.android.draganddrop.data.sources.LocalDataSource
import com.android.draganddrop.local.model.TodoLocal
import io.reactivex.Completable
import io.reactivex.Maybe
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val db: AppDataBase) : LocalDataSource {
    override fun todoList(): Maybe<List<TodoData>> {

        return db.todoDao().observeToDoList().map {
            it.map { todo ->
                with(todo) {
                    TodoData(userId, id, title, completed)
                }
            }
        }
    }

    override fun saveToDo(list: List<TodoData>): Completable {

        return db.todoDao().saveList(list.map {
            with(it) {
                TodoLocal(
                    id = id,
                    userId = userId,
                    title = title,
                    completed = completed
                )
            }
        })
    }

    override fun updateTodoList(params: List<TodoData>): Completable {
        return db.todoDao().updateList(params.map {
            with(it) {
                com.android.draganddrop.local.model.TodoLocal(
                    id = id,
                    userId = userId,
                    title = title,
                    completed = completed
                )
            }
        })
    }

}