package com.example.kabinetzhitelya_kotlin.data.network.models

//import com.google.gson.annotations.SerializedName

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterUserDTO(

    @SerialName("number")
    val number: String,

    @SerialName("last_name")
    val lastName: String,

    @SerialName("email")
    val email: String,

    @SerialName("fcm_token")
    val fcmToken: String,

    @SerialName("device_os")
    val os: String = "Android",

)