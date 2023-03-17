package com.example.basearchitectureproject.user_details.usecase

import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.user_details.database.UserEntity
import com.example.basearchitectureproject.user_details.repository.AdUserToRoomRepository
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import g5.consultency.cuitalibilam.base.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetDataFromDBUseCase constructor(
    private val adUserToRoomRepository : AdUserToRoomRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, List<UserEntity>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(parameters: Unit): Flow<List<UserEntity>> =
        adUserToRoomRepository.loadUser().map { list ->
            list.ifEmpty {
                arrayListOf()
            }
        }
}