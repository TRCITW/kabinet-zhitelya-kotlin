package com.example.kabinetzhitelya_kotlin.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SuccessAuthResponse(

    @SerialName("_token")
    val token: String

)
