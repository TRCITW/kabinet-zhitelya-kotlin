package com.example.kabinetzhitelya_kotlin.ui.auth

import com.example.kabinetzhitelya_kotlin.ui.base.fragment.ErrorHandler

interface AuthView: ErrorHandler {

    fun updateState(state: State)

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }
}