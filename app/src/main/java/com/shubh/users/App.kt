package com.shubh.users

import android.app.Application
import androidx.room.Room
import com.shubh.users.db.UserDatabase

class App:Application() {
    val userDB: UserDatabase by lazy {
        Room.databaseBuilder(
            this,
            UserDatabase::class.java,
            "UserDB"
        ).build()
    }
}