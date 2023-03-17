package com.example.basearchitectureproject.di

import com.example.basearchitectureproject.data.localdb.PersonDao
import com.example.basearchitectureproject.person.repository.DefaultPersonRepository
import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.user_details.database.UserDAO
import com.example.basearchitectureproject.user_details.repository.AdUserToRoomRepository
import com.example.basearchitectureproject.user_details.repository.AdUserToRoomRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepositoryImp

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @ViewModelScoped
    @Provides
    fun providePersonRepository(personDao: PersonDao) : PersonRepository {
        return DefaultPersonRepository(personDao)
    }

    @ViewModelScoped
    @Provides
    fun provideUploadUserDetailsRepository(): UploadUserDetailsRepository {
        return UploadUserDetailsRepositoryImp()
    }
    @ViewModelScoped
    @Provides
    fun provideAdUserToRoomRepository(userDAO: UserDAO): AdUserToRoomRepository {
        return AdUserToRoomRepositoryImp(userDAO)
    }
}