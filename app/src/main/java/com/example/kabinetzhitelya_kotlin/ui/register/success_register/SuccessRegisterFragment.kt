package com.example.kabinetzhitelya_kotlin.ui.register.success_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.databinding.FragmentSuccessRegisterBinding
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment

class SuccessRegisterFragment: BaseFragment() {

    private lateinit var viewBinding: FragmentSuccessRegisterBinding
    private val router = App.INSTANCE.router

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSuccessRegisterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        router.backTo(Screens.AuthScreen())
    }
}