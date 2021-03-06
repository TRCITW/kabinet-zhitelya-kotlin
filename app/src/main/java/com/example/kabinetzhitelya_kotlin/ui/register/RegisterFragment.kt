package com.example.kabinetzhitelya_kotlin.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.databinding.FragmentRegisterBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment
import java.util.regex.Pattern

class RegisterFragment: BaseFragment(), RegisterView {

    private lateinit var viewBinding: FragmentRegisterBinding
    private val presenter = RegisterPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentRegisterBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        presenter.bind(this)
        viewBinding.registerProgressBar.visibility = View.GONE

//        val pattern = Pattern.compile(".+@.+\\.[a-z]+")
//        val matcher = pattern.matcher(viewBinding.emailTextInputRegistration.text.toString())
//        if (matcher.matches()) {
//
//        } else {
//
//        }
//
//        viewBinding

        viewBinding.registerButton.setOnClickListener {
            val accountNumber = viewBinding.accountNumberTextInput.text.toString()
            val name = viewBinding.hostNameTextInput.text.toString()
            val email = viewBinding.emailTextInputRegistration.text.toString()
            presenter.register(accountNumber, name, email)
        }

        viewBinding.cancelButton.setOnClickListener {
            presenter.cancelPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
    }

    override fun updateState(state: RegisterView.State) {
        viewBinding.registerProgressBar.visibility = View.GONE
        when (state) {
            RegisterView.State.LOADING -> {
                viewBinding.registerProgressBar.visibility = View.VISIBLE
            }
            RegisterView.State.SUCCESS -> {
            }
            RegisterView.State.ERROR -> {
            }
        }
    }
}