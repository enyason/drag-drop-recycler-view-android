package com.android.draganddrop.local

import androidx.room.*
import com.android.draganddrop.local.model.TodoLocal
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveList(todo: List<TodoLocal>): Completable

    @Update
    fun updateList(todo: List<TodoLocal>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTodo(todo: TodoLocal): Completable

    @Delete
    fun deleteTodo(todo: TodoLocal)

    @Query("SELECT * FROM todo_table")
    fun observeToDoList(): Maybe<List<TodoLocal>>
}