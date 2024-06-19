package com.shubh.users.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val suite: String,
    val street: String,
    val city: String,
    val zipcode: String
):Parcelable