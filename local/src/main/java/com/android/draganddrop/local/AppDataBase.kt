package com.android.draganddrop.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.draganddrop.local.model.TodoLocal

@Database(
    entities = [TodoLocal::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}