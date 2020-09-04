package com.android.draganddrop.remote.api

import com.android.draganddrop.remote.models.TodoRemote
import io.reactivex.Maybe
import retrofit2.http.GET


interface ToDoApi {

    @GET("users/1/todos")
    fun todos(): Maybe<List<TodoRemote>>
}