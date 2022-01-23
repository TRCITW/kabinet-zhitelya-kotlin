package com.example.kabinetzhitelya_kotlin.ui.pass_recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kabinetzhitelya_kotlin.databinding.FragmentPasswordRecoveryBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment

class RecoveryFragment: BaseFragment(), RecoveryView {

    private lateinit var viewBinding: FragmentPasswordRecoveryBinding
    private val presenter = RecoveryPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        presenter.bind(this)

        viewBinding.sendRecoveryBtn.setOnClickListener {
            val email = viewBinding.emailRecoveryTextInput.text.toString()
            presenter.recoverPassword(email)
        }

        viewBinding.cancelRecoveryBtn.setOnClickListener {
            presenter.cancelPressed()
        }
    }

    override fun updateState(state: RecoveryView.State) {
        viewBinding.recoveryProgressBart.visibility = View.GONE
        when (state) {
            RecoveryView.State.LOADING -> {
                viewBinding.recoveryProgressBart.visibility = View.VISIBLE
            }
            RecoveryView.State.SUCCESS -> {
            }
            RecoveryView.State.ERROR -> {
            }
        }
    }

}