package com.android.draganddrop.remote.models

data class TodoRemote(
    val userId: String,
    val id: Int,
    val title: String,
    val completed: Boolean
)