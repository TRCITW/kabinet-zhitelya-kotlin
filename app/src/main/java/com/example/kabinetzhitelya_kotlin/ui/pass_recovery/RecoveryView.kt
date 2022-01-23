package com.example.kabinetzhitelya_kotlin.ui.pass_recovery

import com.example.kabinetzhitelya_kotlin.ui.base.fragment.ErrorHandler

interface RecoveryView: ErrorHandler {
    fun updateState(state: State)

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }
}