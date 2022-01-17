package com.example.kabinetzhitelya_kotlin.ui.webview

import com.example.kabinetzhitelya_kotlin.ui.base.fragment.ErrorHandler

interface WebviewView: ErrorHandler {
    fun loadWebview(withCookie: String)
}