package com.example.basearchitectureproject.data.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.basearchitectureproject.data.Person
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Query("SELECT * FROM Person ORDER BY name DESC")
    fun getPersons(): Flowable<List<Person>>

    @Query("SELECT * FROM Person ORDER BY name DESC")
    fun getAllPersons(): Flow<List<Person>>

    @Query("SELECT * FROM Person WHERE name == :name LIMIT 1")
    fun getPerson(name: Long): Flow<Person>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)


    fun insertPerson(person: Person){
        insert(person)
    }
//
//    suspend fun insertPersonAsync(person: Person){
//        insertAsync(person)
//    }
}