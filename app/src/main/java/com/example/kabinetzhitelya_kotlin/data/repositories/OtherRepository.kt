package com.example.kabinetzhitelya_kotlin.data.repositories

import com.example.kabinetzhitelya_kotlin.data.network.RetrofitClient
import com.example.kabinetzhitelya_kotlin.data.network.models.QRCodeDTO
import com.example.kabinetzhitelya_kotlin.data.network.models.QRCodeResponse
import io.reactivex.Flowable

object OtherRepository {

    private val retrofitClient = RetrofitClient.endpointsInterface

    fun requestBankUrlFromQR(qrCode: String): Flowable<QRCodeResponse> {
        val dto = QRCodeDTO(qrCode)
        return retrofitClient.requestBankUrlFromQR(dto)
            .toFlowable()
    }

}