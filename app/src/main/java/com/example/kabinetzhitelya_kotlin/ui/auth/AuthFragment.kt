package com.example.kabinetzhitelya_kotlin.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import com.example.kabinetzhitelya_kotlin.databinding.FragmentAuthBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment


class AuthFragment: BaseFragment(), AuthView {

    private lateinit var viewBinding: FragmentAuthBinding
    private val presenter = AuthPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)

        viewBinding.progressBar.visibility = View.GONE

        viewBinding.passwordTextInput.visibility = View.GONE

        viewBinding.forgetPassBtn.setOnClickListener {
            presenter.navigateToPassRecovery()
        }

        viewBinding.createAccountBtn.setOnClickListener {
            presenter.navigateToCreateAccount()
        }

        viewBinding.payByQrBtn.setOnClickListener {
            presenter.navigateToQRCodeScan()
        }

        viewBinding.telegramBtn.setOnClickListener {
            presenter.navigateToTelegramBot()
        }

        viewBinding.emailTextInput.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >=
                    viewBinding.emailTextInput.right -
                    viewBinding.emailTextInput.paddingLeft -
                    viewBinding.emailTextInput.compoundDrawables
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {
                    viewBinding.passwordTextInput.visibility = View.VISIBLE
                    return@OnTouchListener true
                }
            }
            false
        })

        viewBinding.passwordTextInput.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >=
                    viewBinding.passwordTextInput.right -
                    viewBinding.passwordTextInput.paddingLeft -
                    viewBinding.passwordTextInput.compoundDrawables
                        .get(DRAWABLE_RIGHT).getBounds().width()
                ) {
                    val email = viewBinding.emailTextInput.text.toString()
                    val password = viewBinding.passwordTextInput.text.toString()
                    presenter.login(email, password)
                    return@OnTouchListener true
                }
            }
            false
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun updateState(state: AuthView.State) {
        viewBinding.progressBar.visibility = View.GONE
        when (state) {
            AuthView.State.LOADING -> {
                viewBinding.progressBar.visibility = View.VISIBLE
            }
            AuthView.State.SUCCESS -> {
            }
            AuthView.State.ERROR -> {
            }
        }
    }
}