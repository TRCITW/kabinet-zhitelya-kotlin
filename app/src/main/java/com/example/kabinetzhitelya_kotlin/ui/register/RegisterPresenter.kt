package com.example.kabinetzhitelya_kotlin.ui.register

import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.SerialName

class RegisterPresenter {

    private val compositeDisposable = CompositeDisposable()
    private var registerView: RegisterView? = null
    private val authInteractor = AuthInteractor()
    private val router = App.INSTANCE.router

    fun bind(view: RegisterView) {
        registerView = view
    }

    fun unbind() {
        registerView = null
        compositeDisposable.clear()
    }

    fun register(number: Int, lastName: String, email: String) = authInteractor.register(number, lastName, email)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            registerView?.updateState(RegisterView.State.LOADING)
        }
        .subscribeBy(
            onNext = {
                registerView?.updateState(RegisterView.State.SUCCESS)
                router.navigateTo(Screens.SuccessRegisterScreen())
            },
            onError = { error ->
                if (error is HttpException) {
                    val code = error.code()
                    when (code) {
                        400 -> {
                            registerView?.updateState(RegisterView.State.ERROR)
                            registerView?.showError(error.localizedMessage)
                        }
                    }
                }
            }
        )

    fun cancelPressed() {
        router.backTo(Screens.AuthScreen())
    }
}