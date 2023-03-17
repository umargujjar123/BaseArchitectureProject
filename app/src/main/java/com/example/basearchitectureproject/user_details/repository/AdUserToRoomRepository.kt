package com.example.basearchitectureproject.user_details.repository

import android.util.Log
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.data.localdb.PersonDao
import com.example.basearchitectureproject.user_details.database.UserDAO
import com.example.basearchitectureproject.user_details.database.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AdUserToRoomRepository {
    fun loadUser(): Flow<List<UserEntity>>
    suspend fun addUser(user: UserEntity): Flow<Int>
}

class AdUserToRoomRepositoryImp @Inject constructor(
    private val userDAO: UserDAO,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : AdUserToRoomRepository {
    override fun loadUser(): Flow<List<UserEntity>> {
        return userDAO.getAllUser();
    }

    override suspend fun addUser(user: UserEntity) =  flow {
        emit(1)
        userDAO.saveUser(user)
        Log.e("TAG", "addUser: User is saved", )
        delay(2000)
        emit(2)
    }
}