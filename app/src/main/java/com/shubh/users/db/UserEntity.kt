package com.shubh.users.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shubh.users.model.Address

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val phone: String,
    val email: String,
    val address: Address,
    val companyName: String,
    val isFavorite: Boolean,
    val isEdited:Boolean
)