package com.shubh.users.db

import androidx.room.TypeConverter
import com.shubh.users.model.Address

class Converters {

    @TypeConverter
    fun fromAddress(address: Address):String{
        return "${address.suite},${address.street},${address.city},${address.zipcode}"
    }

    @TypeConverter
    fun toAddress(address:String):Address{
        val addressDetails = address.split(",")
        return Address(addressDetails.first(),addressDetails.component2(),addressDetails.component3(),addressDetails.last())
    }
}