package com.example.kabinetzhitelya_kotlin.ui.scan_qr

import com.example.kabinetzhitelya_kotlin.ui.base.fragment.ErrorHandler

interface ScanQRView: ErrorHandler {

    fun updateState(state: State)

    fun openBankURL(url: String)

    enum class State {
        LOADING,
        SUCCESS,
        ERROR
    }

}