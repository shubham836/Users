package com.shubh.users.db

import androidx.room.TypeConverter
import com.shubh.users.model.Address
import com.shubh.users.model.Company

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

    @TypeConverter
    fun fromCompany(company:Company):String{
        return company.name
    }

    @TypeConverter
    fun toCompany(companyName:String):Company{
        return Company(companyName)
    }
}