package com.example.basearchitectureproject.person.usecases

import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.util.usecase.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class PersonsUseCase constructor(
    private val personRepository: PersonRepository,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FlowUseCase<Unit, List<Person>>(dispatcher) {

    @ExperimentalCoroutinesApi
    override fun execute(parameters: Unit): Flow<List<Person>> =
        personRepository.loadPersons().map { list ->
            list.ifEmpty {
                arrayListOf()
            }
        }
}