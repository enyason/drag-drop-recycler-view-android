package com.android.draganddrop.data.sources

import com.android.draganddrop.data.model.TodoData
import io.reactivex.Maybe

interface RemoteDataSource {

    fun todoList(): Maybe<List<TodoData>>
}