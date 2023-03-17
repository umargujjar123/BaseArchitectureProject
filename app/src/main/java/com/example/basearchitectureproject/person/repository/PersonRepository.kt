package com.example.basearchitectureproject.person.repository

import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.data.localdb.PersonDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PersonRepository {
    fun loadPersons(): Flow<List<Person>>
    suspend fun loadPersonsRemote(): Flow<List<Person>>
    suspend fun addPerson(person: Person): Flow<Int>
}

class DefaultPersonRepository @Inject constructor(
    private val personDao: PersonDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PersonRepository {
    private var personListCache: List<Person>? = null
    override fun loadPersons(): Flow<List<Person>> {
        return personDao.getAllPersons();
    }

    override suspend fun loadPersonsRemote() = flow {
        personListCache?.let { personList ->
            emit(personList)
        }

        delay(2000)

        withContext(dispatcher) {
            loadPersons().collectLatest {
                personListCache = it
                emit(it)
            }
        }
    }

    override suspend fun addPerson(person: Person) =  flow {
        emit(1)
        personDao.insert(person)
        delay(2000)
        emit(2)
    }
}