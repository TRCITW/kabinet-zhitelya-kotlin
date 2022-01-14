package com.example.kabinetzhitelya_kotlin.ui.register

import com.example.kabinetzhitelya_kotlin.ui.base.fragment.ErrorHandler

interface RegisterView: ErrorHandler {

    fun updateState(state: State)

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }
}