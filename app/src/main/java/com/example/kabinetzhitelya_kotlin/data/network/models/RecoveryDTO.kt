package com.example.kabinetzhitelya_kotlin.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecoveryDTO (

    @SerialName("username")
    val username: String

)