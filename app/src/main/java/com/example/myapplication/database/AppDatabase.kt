package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.dao.UserDao
import com.example.myapplication.dto.UserEntity


@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        fun initialize(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, "my-application-09")
                .allowMainThreadQueries().build()
    }
}