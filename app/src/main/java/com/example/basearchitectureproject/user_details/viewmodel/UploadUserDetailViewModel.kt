package com.example.basearchitectureproject.user_details.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.example.basearchitectureproject.base.BaseViewModel
import com.example.basearchitectureproject.data.Person
import com.example.basearchitectureproject.user_details.database.UserEntity
import com.example.basearchitectureproject.user_details.repository.AdUserToRoomRepository
import com.example.basearchitectureproject.user_details.usecase.GetAllUserDetailsUsesCase
import com.example.basearchitectureproject.user_details.usecase.GetDataFromDBUseCase
import com.example.basearchitectureproject.user_details.usecase.UploadUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import g5.consultency.cuitalibilam.base.util.Event
import g5.consultency.cuitalibilam.user_details.models.UploadUserDetailsModel
import g5.consultency.cuitalibilam.user_details.models.UserDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadUserDetailViewModel @Inject constructor(
    private val uploadUserDetailsUseCase: UploadUserDetailsUseCase,
    private val getAllUserDetailsUsesCase: GetAllUserDetailsUsesCase,
    private val adUserToRoomRepository : AdUserToRoomRepository,
    private val getDataFromDBUseCase : GetDataFromDBUseCase,

    ) : BaseViewModel() {


    //Actions
    private val _rootClick = MutableLiveData<Event<Unit>>()
    val rootClick: LiveData<Event<Unit>> = _rootClick
    private val userDetailsModel : UserDetailsModel = UserDetailsModel()

    private val _backButton = MutableLiveData<Event<Unit>>()
    val backBtn: LiveData<Event<Unit>> = _backButton

    private val _fetchUserDetails: MutableLiveData<Unit> = MutableLiveData()

    val userDetails = Transformations.switchMap(_fetchUserDetails) {
        getAllUserDetailsUsesCase(it).asLiveData()
    }

    private val _getDataFromDB: MutableLiveData<Unit> = MutableLiveData()

    val getDataFromDB = Transformations.switchMap(_getDataFromDB) {
        getDataFromDBUseCase(it).asLiveData()
    }

    var uploadUserDetailsModel = Transformations.map(userDetails) {
        UploadUserDetailsModel(userDetails = it.data ?: UserDetailsModel(
                email = "",
                userName = "",
                userMobileNumber = "",
            )
        )
    }
    private val _inituploadDetailMLD: MutableLiveData<UploadUserDetailsModel> = MutableLiveData()

    val initUploadDetailLD = Transformations.switchMap(_inituploadDetailMLD) {
        uploadUserDetailsUseCase(it).asLiveData()

    }
fun uploadRoomData(){
    addUser(UserEntity(email =userDetailsModel.email?:"", userName = userDetailsModel.userName?:"", userMobileNumber = userDetailsModel.userMobileNumber?:""))
}

    fun uploadUserDetails() {

        addUser(UserEntity(email =userDetailsModel.email?:"", userName = userDetailsModel.userName?:"", userMobileNumber = userDetailsModel.userMobileNumber?:""))

        viewModelScope.launch(Dispatchers.IO) {
            val userDetails = uploadUserDetailsModel.value
            _inituploadDetailMLD.postValue(userDetails)
        }
    }

    fun backBtn() {
        _backButton.value = Event(Unit)
    }

    fun rootClicked() {
        _rootClick.value = Event(Unit)
    }

    fun showFirebaseData(){
        _fetchUserDetails.value = Unit
    }
    fun showDBData(){
        _getDataFromDB.value = Unit
    }
    fun addUser(user: UserEntity){
        GlobalScope.launch(Dispatchers.IO) {
            adUserToRoomRepository.addUser(user).collect {
                when(it) {
                    1 -> {
                        Log.e("TAG", "addUser: Data is saved", )
                        setLoading(true)
                    }
                    2 -> {
                        Log.e("TAG", "addUser: Data is saved", )
                        setLoading(false)
                    }
                }
            }
        }
    }
}

