package com.shubh.users.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.shubh.users.model.Address
import com.shubh.users.model.Company

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: Address,
    val companyName: Company,
    val isFavorite: Boolean,
    val isEdited:Boolean
)