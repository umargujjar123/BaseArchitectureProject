package com.example.basearchitectureproject.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.auto.value.AutoValue
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
@Entity
data class Person @JvmOverloads constructor(
    @PrimaryKey
    var name: String = "",
    var age: Double = 0.0
): Serializable
