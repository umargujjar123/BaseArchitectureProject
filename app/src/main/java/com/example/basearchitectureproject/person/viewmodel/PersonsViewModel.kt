package com.example.basearchitectureproject.person.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.common.Event
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.person.usecases.PersonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val personsUseCase: PersonsUseCase,
    private val personRepository: PersonRepository,
) : BaseViewModel() {

    val persons: LiveData<List<Person>> = personsUseCase(Unit).asLiveData()
    var PerosnList = emptyList<Person>()

    //Actions
    private val _addPersonAction = MutableLiveData<Event<Unit>>()
    val addPersonAction: LiveData<Event<Unit>> = _addPersonAction

    //Actions
    private val _personDetailAction = MutableLiveData<Person>()
    val personDetailAction: LiveData<Person> = _personDetailAction


    fun personClickListener(person: Person) {
        _personDetailAction.value = person
    }


    fun actionAddPerson() {
        _addPersonAction.value = Event(Unit)
    }


}