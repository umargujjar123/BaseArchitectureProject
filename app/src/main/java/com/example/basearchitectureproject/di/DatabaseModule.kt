package com.example.basearchitectureproject.di

import android.content.Context
import com.example.basearchitectureproject.data.localdb.PersonDao
import com.example.basearchitectureproject.data.localdb.RoomDB
import com.example.basearchitectureproject.user_details.database.UserDAO
import com.google.firebase.firestore.auth.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): RoomDB {
        return RoomDB.init(context)
    }

    @Singleton
    @Provides
    fun provideProductsDao(roomDB: RoomDB): PersonDao {
        return roomDB.personDao()
    }

    @Singleton
    @Provides
    fun provideUserDao(roomDB: RoomDB): UserDAO {
        return roomDB.userDao()
    }
}