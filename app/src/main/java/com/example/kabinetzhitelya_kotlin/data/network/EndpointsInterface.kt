package com.example.kabinetzhitelya_kotlin.data.network

import com.example.kabinetzhitelya_kotlin.data.network.models.LoginUserDTO
import com.example.kabinetzhitelya_kotlin.data.network.models.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface EndpointsInterface {

    @POST("auth/login/")
    fun login(
        @Body params: LoginUserDTO
    ): Single<SuccessAuthResponse>

    @POST("registration/sign_in/")
    fun register(
        @Body params: RegisterUserDTO
    ): Single<SuccessAuthResponse>

    @POST("registration/reset_password/")
    fun recovery(
        @Body dto: RecoveryDTO
    ): Completable

    @POST("public/bank_redirect_by_qr/")
    fun requestBankUrlFromQR(
        @Body dto: QRCodeDTO
    ): Single<QRCodeResponse>

}