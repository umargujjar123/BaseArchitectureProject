package com.example.basearchitectureproject.person.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.di.CoroutinesDispatcherProvider
import com.example.basearchitectureproject.person.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class AddPersonViewModel @Inject constructor(
    private val personRepository: PersonRepository,
    private val coroutineDispatcherProvider: CoroutinesDispatcherProvider,
) :
    BaseViewModel() {
    private val coroutineScope = CoroutineScope(coroutineDispatcherProvider.main)

    //Actions
    private val _addPersonAction = MutableLiveData<Unit>()
    val addPersonAction: LiveData<Unit> = _addPersonAction


    fun addPerson(person: Person){
        coroutineScope.launch(Dispatchers.IO) {
            personRepository.addPerson(person).collect {
                when(it) {
                    1 -> {
                        setLoading(true)
                    }
                    2 -> {
                        setLoading(false)
                    }
                }
            }
        }
    }

    fun addPersonAction() {
        Log.e("TAG", "AddPersonViewModel --> addPersonAction: called")
        _addPersonAction.value = Unit

    }
}