package com.example.basearchitectureproject.di

import com.example.basearchitectureproject.person.repository.PersonRepository
import com.example.basearchitectureproject.person.usecases.PersonsUseCase
import com.example.basearchitectureproject.user_details.repository.AdUserToRoomRepository
import com.example.basearchitectureproject.user_details.usecase.GetAllUserDetailsUsesCase
import com.example.basearchitectureproject.user_details.usecase.GetDataFromDBUseCase
import com.example.basearchitectureproject.user_details.usecase.UploadUserDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import g5.consultency.cuitalibilam.user_details.repository.UploadUserDetailsRepository
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @ViewModelScoped
    @Provides
    fun providePersonsUseCase(
        personRepository: PersonRepository
    ): PersonsUseCase {
        return PersonsUseCase(
            personRepository,
            Dispatchers.IO
        )
    }

    @ViewModelScoped
    @Provides
    fun provideUploadUserDetailsUseCase(
        uploadUserDetailsRepository: UploadUserDetailsRepository,
    ): UploadUserDetailsUseCase {
        return UploadUserDetailsUseCase(
            uploadUserDetailsRepository = uploadUserDetailsRepository
        )
    }


    @ViewModelScoped
    @Provides
    fun provideGetAllUserDetailsUsesCase(
        uploadUserDetailsRepository: UploadUserDetailsRepository,
    ): GetAllUserDetailsUsesCase {
        return GetAllUserDetailsUsesCase(
            uploadUserDetailsRepository = uploadUserDetailsRepository
        )
    }

    @ViewModelScoped
    @Provides
    fun provideGetDataFromDBUseCase(
        adUserToRoomRepository : AdUserToRoomRepository,
    ): GetDataFromDBUseCase {
        return GetDataFromDBUseCase(
            adUserToRoomRepository = adUserToRoomRepository
        )
    }
}