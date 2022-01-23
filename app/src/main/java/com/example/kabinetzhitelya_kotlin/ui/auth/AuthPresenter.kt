package com.example.kabinetzhitelya_kotlin.ui.auth

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
import com.example.kabinetzhitelya_kotlin.ui.Screens
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

class AuthPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val authInteractor = AuthInteractor()
    private val router = App.INSTANCE.router
    private var authView: AuthView? = null

    fun bind(view: AuthView) {
        this.authView = view
    }

    fun unbind() {
        this.authView = null
        compositeDisposable.clear()
    }

    fun login(username: String, password: String) = authInteractor.login(username, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            authView?.updateState(AuthView.State.LOADING)
        }
        .subscribeBy(
            onNext = {
                router.newRootScreen(Screens.WebviewScreen(null))
                authView?.updateState(AuthView.State.SUCCESS)
            },
            onError = { error ->
                if (error is HttpException) {
                    val code = error.code()
                    when (code) {
                        403 -> authView?.showError("Invalid login or password")
                    }
                }
                authView?.updateState(AuthView.State.ERROR)
            }
        ).addTo(compositeDisposable)


    fun navigateToTelegramBot() {
////        val url = getString()
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    fun navigateToQRCodeScan() {
    }

    fun navigateToPassRecovery() {
        router.navigateTo(Screens.RecoveryPassScreen())
    }

    fun navigateToCreateAccount() {
        router.navigateTo(Screens.RegisterScreen())
    }

}