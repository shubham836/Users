package com.shubh.users

import com.shubh.users.api.RetrofitHelper
import com.shubh.users.db.UserDatabase
import com.shubh.users.db.UserEntity

class UserRepository(val userDB: UserDatabase) {

    suspend fun getUsers() = RetrofitHelper.userAPI.getUsers()

    suspend fun getFavoriteUsers() = userDB.getUsersDao().getFavoriteUsers()

    suspend fun addUser(user: UserEntity) =
        userDB.getUsersDao().addUser(user)

    suspend fun getUserById(userId: Int): UserEntity? =
        userDB.getUsersDao().getUserById(userId)


    suspend fun removeUser(user: UserEntity) =
        userDB.getUsersDao().removeFromFavorites(user)


    suspend fun updateUser(user: UserEntity) =
        userDB.getUsersDao().updateUser(user)

}