package com.example.kabinetzhitelya_kotlin.ui

import com.example.kabinetzhitelya_kotlin.ui.auth.AuthFragment
import com.example.kabinetzhitelya_kotlin.ui.register.RegisterFragment
import com.example.kabinetzhitelya_kotlin.ui.register.success_register.SuccessRegisterFragment
import com.example.kabinetzhitelya_kotlin.ui.webview.WebviewFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun AuthScreen() = FragmentScreen { AuthFragment() }

    fun RegisterScreen() = FragmentScreen { RegisterFragment() }

    fun SuccessRegisterScreen() = FragmentScreen { SuccessRegisterFragment() }

    fun WebviewScreen() = FragmentScreen { WebviewFragment() }

}