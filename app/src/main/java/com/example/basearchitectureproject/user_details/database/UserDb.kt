package com.example.basearchitectureproject.user_details.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(UserEntity::class)],version = 1)
abstract class UserDb : RoomDatabase() {
    abstract fun userDao(): UserDAO
}