package com.shubh.users.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shubh.users.UserRepository
import com.shubh.users.UserResponse
import com.shubh.users.db.UserEntity
import com.shubh.users.model.User
import kotlinx.coroutines.launch
import org.json.JSONObject

private const val TAG = "UserViewModel"

class UserViewModel(val userRepository: UserRepository) : ViewModel() {
    private var _userList: MutableLiveData<UserResponse<List<User>>> = MutableLiveData()
    val userList: LiveData<UserResponse<List<User>>> get() = _userList
    private var _favoriteUserList: MutableLiveData<List<UserEntity>> = MutableLiveData()
    val favoriteUserList: LiveData<List<UserEntity>> get() = _favoriteUserList
    private var _userEntity: MutableLiveData<UserEntity?> = MutableLiveData()
    val userEntity: LiveData<UserEntity?> get() = _userEntity

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = userRepository.getUsers()
                _userList.postValue(UserResponse.Loading())
                if (response.isSuccessful && response.body() != null) {
                    _userList.postValue(UserResponse.Success(response.body()!!))
                }else{
                    _userList.postValue(UserResponse.Error("Something went wrong"))
                }
            } catch (e: Exception) {
                _userList.postValue(UserResponse.Error(e.message!!))
            }
        }
    }

    fun getFavoriteUsers() {
        viewModelScope.launch {
            try {
                val userList = userRepository.getFavoriteUsers()
                _favoriteUserList.postValue(userList)
            } catch (e: Exception) {
                Log.e(TAG, "getFavoriteUsers: ${e.stackTraceToString()}")
            }
        }
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }

    fun removeUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.removeUser(user)
        }
    }

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            try {
                userRepository.updateUser(user)
            } catch (e: Exception) {
                Log.e(TAG, "updateUser: ${e.stackTraceToString()}")
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            val response = userRepository.getUserById(userId)
            _userEntity.postValue(response)
        }
    }
}