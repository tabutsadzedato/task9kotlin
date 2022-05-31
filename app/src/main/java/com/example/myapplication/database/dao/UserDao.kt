package com.example.myapplication.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.myapplication.dto.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = REPLACE)
    fun insertUsers(users: List<UserEntity>)

    @Query("DELETE FROM user WHERE roomId NOT IN (SELECT roomId FROM user LIMIT 10)")
    fun removeExcessUsers()

    @Query("SELECT * FROM user")
    fun getUsers(): List<UserEntity>
}