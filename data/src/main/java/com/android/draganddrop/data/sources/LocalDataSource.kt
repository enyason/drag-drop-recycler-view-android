package com.android.draganddrop.data.sources

import com.android.draganddrop.data.model.TodoData
import io.reactivex.Completable
import io.reactivex.Maybe

interface LocalDataSource {

    fun todoList(): Maybe<List<TodoData>>
    fun saveToDo(list: List<TodoData>): Completable
    fun updateTodoList(params: List<TodoData>): Completable

}