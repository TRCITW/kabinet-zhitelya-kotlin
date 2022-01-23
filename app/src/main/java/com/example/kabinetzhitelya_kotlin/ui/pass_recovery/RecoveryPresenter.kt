package com.example.kabinetzhitelya_kotlin.ui.pass_recovery

import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class RecoveryPresenter {

    private val compositeDisposable = CompositeDisposable()
    private val authInteractor = AuthInteractor()
    private val router = App.INSTANCE.router
    private var recoveryView: RecoveryView? = null

    fun bind(view: RecoveryView) {
        this.recoveryView = view
    }

    fun unbind() {
        this.recoveryView = null
        compositeDisposable.clear()
    }

    fun recoverPassword(email: String) = authInteractor.recoveryPass(email)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            recoveryView?.updateState(RecoveryView.State.LOADING)
        }
        .subscribeBy(
            onNext = {
                router.newRootScreen(Screens.RecoverySuccessScreen())
                recoveryView?.updateState(RecoveryView.State.SUCCESS)
            },
            onError = { error ->
                if (error is HttpException) {
                    val code = error.code()
                    when (code) {
                        403 -> recoveryView?.showError("Invalid login or password")
                    }
                }
                recoveryView?.updateState(RecoveryView.State.ERROR)
            }
        ).addTo(compositeDisposable)

    fun cancelPressed() {
        router.backTo(Screens.AuthScreen())
    }
}