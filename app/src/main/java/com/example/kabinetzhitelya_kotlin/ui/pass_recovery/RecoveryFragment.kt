package com.example.kabinetzhitelya_kotlin.ui.pass_recovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.kabinetzhitelya_kotlin.databinding.FragmentPasswordRecoveryBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment
import com.example.kabinetzhitelya_kotlin.ui.webview.WebviewFragment

class RecoveryFragment: BaseFragment(), RecoveryView {

    private lateinit var viewBinding: FragmentPasswordRecoveryBinding
    private val presenter = RecoveryPresenter()

    companion object {
        const val EMAIL = "email"

        fun newInstance(email: String?): RecoveryFragment {
            val fragment = RecoveryFragment()
            fragment.arguments = bundleOf(
                EMAIL to email
            )
            return fragment
        }
    }

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

        val bundle = requireArguments()
        val email = bundle.getString(EMAIL)
        viewBinding.emailRecoveryTextInput.setText(email)

        viewBinding.sendRecoveryBtn.setOnClickListener {
            val email = viewBinding.emailRecoveryTextInput.text.toString()
            presenter.recoverPassword(email)
        }

        viewBinding.cancelRecoveryBtn.setOnClickListener {
            presenter.cancelPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbind()
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