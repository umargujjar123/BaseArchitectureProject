package com.example.basearchitectureproject.user_details.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class UserEntity @JvmOverloads constructor(
    @PrimaryKey
    var email: String = "",
    var userName: String = "",
    var userMobileNumber: String = "",
): Serializable