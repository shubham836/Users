package com.shubh.users.api

import com.shubh.users.model.User
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}