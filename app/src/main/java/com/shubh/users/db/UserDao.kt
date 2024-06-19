package com.shubh.users.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UsersDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getFavoriteUsers(): List<UserEntity>

    @Query("SELECT * FROM UserEntity where id==:userId")
    suspend fun getUserById(userId:Int): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserEntity)

    @Delete
    suspend fun removeFromFavorites(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)
}