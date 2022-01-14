package com.example.kabinetzhitelya_kotlin.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QRCodeDTO (

    @SerialName("text")
    val text: String

)
