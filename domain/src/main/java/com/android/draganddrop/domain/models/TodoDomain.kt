package com.android.draganddrop.domain.models

data class TodoDomain(
    val userId: String,
    val id: Int,
    val title: String,
    val completed: Boolean
)