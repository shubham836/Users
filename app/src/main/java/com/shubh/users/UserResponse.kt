package com.shubh.users

sealed class UserResponse<T>(val data:T? = null,val message:String? = null) {
    class Success<T>(data: T):UserResponse<T>(data)
    class Error<T>(message:String):UserResponse<T>(message = message)
    class Loading<T>():UserResponse<T>()
}