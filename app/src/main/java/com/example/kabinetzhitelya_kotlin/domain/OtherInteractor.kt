package com.example.kabinetzhitelya_kotlin.domain

import com.example.kabinetzhitelya_kotlin.data.network.models.QRCodeDTO
import com.example.kabinetzhitelya_kotlin.data.network.models.QRCodeResponse
import com.example.kabinetzhitelya_kotlin.data.repositories.OtherRepository
import io.reactivex.Flowable

class OtherInteractor {

    fun requestBankUrlFromQR(qrCode: String): Flowable<QRCodeResponse> {
        return OtherRepository.requestBankUrlFromQR(qrCode)
    }
}