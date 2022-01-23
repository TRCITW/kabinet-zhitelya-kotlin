package com.example.kabinetzhitelya_kotlin.data.network.models

import kotlinx.serialization.*

@Serializable
data class LoginUserDTO(

    @SerialName("username")
    val username: String,

    @SerialName("password")
    val password: String,

    @SerialName("fcm_token")
    val fcmToken: String

)
