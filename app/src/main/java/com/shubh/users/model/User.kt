package com.shubh.users.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val address: Address,
    val company: Company
)