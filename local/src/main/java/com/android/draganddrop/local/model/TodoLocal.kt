package com.android.draganddrop.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoLocal(
    @PrimaryKey
    val id: Int,
    val userId: String,
    val title: String,
    val completed: Boolean
)