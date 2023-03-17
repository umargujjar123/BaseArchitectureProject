package com.example.basearchitectureproject.user_details.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.flow.Flow
@Dao

interface UserDAO

{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: UserEntity)

    @Query(value = "Select * from UserEntity")
    fun getAllUser() : Flow<List<UserEntity>>
}