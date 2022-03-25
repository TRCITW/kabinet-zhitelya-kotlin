package com.example.kabinetzhitelya_kotlin.ui.scan_qr

import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
import com.example.kabinetzhitelya_kotlin.domain.OtherInteractor
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.example.kabinetzhitelya_kotlin.ui.register.RegisterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ScanQRPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var scanQRView: ScanQRView? = null
    private val otherInteractor = OtherInteractor()
    private val router = App.INSTANCE.router

    fun bind(view: ScanQRView) {
        scanQRView = view
    }

    fun unbind() {
        scanQRView = null
        compositeDisposable.clear()
    }

    fun sendCode(code: String) = otherInteractor.requestBankUrlFromQR(code)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            scanQRView?.updateState(ScanQRView.State.LOADING)
        }
        .subscribeBy(
            onNext = { resDto ->
                scanQRView?.updateState(ScanQRView.State.SUCCESS)
                scanQRView?.openBankURL(resDto.bankUrl)
                router.backTo(Screens.AuthScreen())
            },
            onError = { error ->
                scanQRView?.updateState(ScanQRView.State.ERROR)
                scanQRView?.showError(error.toString())
            }
        ).addTo(compositeDisposable)

}