package com.example.kabinetzhitelya_kotlin.ui.webview

import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.domain.AuthInteractor
import com.example.kabinetzhitelya_kotlin.ui.Screens
import io.reactivex.disposables.CompositeDisposable

class WebviewPresenter {

    private val authInteractor = AuthInteractor()
    private val compositeDisposable = CompositeDisposable()
    private var webviewView: WebviewView? = null
    private val router = App.INSTANCE.router

    fun bind(view: WebviewView) {
        webviewView = view
        val cookie = authInteractor.getCookie()
        webviewView?.loadWebview(cookie)
    }

    fun unbind() {
        webviewView = null
        compositeDisposable.clear()
    }

    fun navigateToAuth() {
        authInteractor.clearCookie()
        router.newRootScreen(Screens.AuthScreen())
    }
}