package com.example.kabinetzhitelya_kotlin.data.network.models

import kotlinx.serialization.*

@Serializable
data class ErrorResponse (

    @SerialName("message")
    val message: String

)