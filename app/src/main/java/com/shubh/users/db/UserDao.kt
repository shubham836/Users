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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteUser(user: UserEntity)

    @Delete
    suspend fun deleteFavoriteUser(user: UserEntity)

    @Update
    suspend fun updateUserAddress(user: UserEntity)
}