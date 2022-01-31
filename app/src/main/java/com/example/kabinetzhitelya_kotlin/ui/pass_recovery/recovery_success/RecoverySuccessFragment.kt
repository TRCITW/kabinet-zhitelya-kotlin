package com.example.kabinetzhitelya_kotlin.ui.pass_recovery.recovery_success

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.databinding.FragmentRecoverySuccessBinding
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment

class RecoverySuccessFragment: BaseFragment() {

    private lateinit var viewBinding: FragmentRecoverySuccessBinding
    private val router = App.INSTANCE.router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRecoverySuccessBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        viewBinding.recoverySuccessCloseBtn.setOnClickListener {
            router.newRootScreen(Screens.AuthScreen())
        }
    }

}