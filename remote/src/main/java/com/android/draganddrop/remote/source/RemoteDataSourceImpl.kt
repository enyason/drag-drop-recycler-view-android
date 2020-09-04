package com.android.draganddrop.remote.source

import com.android.draganddrop.data.model.TodoData
import com.android.draganddrop.data.sources.RemoteDataSource
import com.android.draganddrop.remote.api.ToDoApi
import io.reactivex.Maybe
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val api: ToDoApi) : RemoteDataSource {
    override fun todoList(): Maybe<List<TodoData>> {

        return api.todos().map { todos ->
            todos.map { todoRemote -> with(todoRemote) { TodoData(userId, id, title, completed) } }
        }
    }

}