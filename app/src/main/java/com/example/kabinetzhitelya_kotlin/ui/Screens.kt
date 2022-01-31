package com.example.kabinetzhitelya_kotlin.ui

import com.example.kabinetzhitelya_kotlin.ui.auth.AuthFragment
import com.example.kabinetzhitelya_kotlin.ui.pass_recovery.RecoveryFragment
import com.example.kabinetzhitelya_kotlin.ui.pass_recovery.RecoveryView
import com.example.kabinetzhitelya_kotlin.ui.pass_recovery.recovery_success.RecoverySuccessFragment
import com.example.kabinetzhitelya_kotlin.ui.register.RegisterFragment
import com.example.kabinetzhitelya_kotlin.ui.register.success_register.SuccessRegisterFragment
import com.example.kabinetzhitelya_kotlin.ui.scan_qr.ScanQRFragment
import com.example.kabinetzhitelya_kotlin.ui.webview.WebviewFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

    fun AuthScreen() = FragmentScreen { AuthFragment() }

    fun RegisterScreen() = FragmentScreen { RegisterFragment() }

    fun SuccessRegisterScreen() = FragmentScreen { SuccessRegisterFragment() }

    fun WebviewScreen(link: String?) = FragmentScreen { WebviewFragment.newInstance(link) }

    fun RecoveryPassScreen(email: String?) = FragmentScreen { RecoveryFragment.newInstance(email) }

    fun RecoverySuccessScreen() = FragmentScreen { RecoverySuccessFragment() }

    fun ScanQRCodeScreen() = FragmentScreen { ScanQRFragment() }

}