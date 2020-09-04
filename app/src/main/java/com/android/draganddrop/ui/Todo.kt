package com.android.draganddrop.ui

import androidx.recyclerview.widget.DiffUtil

data class Todo(
    val userId: String,
    val id: Int,
    val title: String,
    val completed: Boolean
)

class ToDoDiffUtil : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }

}