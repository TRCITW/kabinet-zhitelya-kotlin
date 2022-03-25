package com.example.kabinetzhitelya_kotlin.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.databinding.FragmentAuthBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.hideKeyboard
import java.util.regex.Pattern


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.bind(this)

        viewBinding.progressBar.visibility = View.GONE
        viewBinding.passwordTextInput.visibility = View.GONE

        viewBinding.forgetPassBtn.setOnClickListener {
            val email = viewBinding.emailTextInput.text.toString()
            presenter.navigateToPassRecovery(email)
        }

        viewBinding.createAccountBtn.setOnClickListener {
            presenter.navigateToCreateAccount()
        }

        viewBinding.payByQrBtn.setOnClickListener {
            presenter.navigateToQRCodeScan()
        }

        viewBinding.telegramBtn.setOnClickListener {
            val url = getString(R.string.telegram_bot_url)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
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
                    val pattern = Pattern.compile(".+@.+\\.[a-z]+")
                    val matcher = pattern.matcher(viewBinding.emailTextInput.text.toString())
                    if (matcher.matches()) {
                        viewBinding.passwordTextInput.visibility = View.VISIBLE
                        viewBinding.passwordTextInput.requestFocus()
                        viewBinding.forgetPassBtn.visibility = View.VISIBLE
                        val icon = R.drawable.ic_ready
                        viewBinding.emailTextInput.setCompoundDrawablesWithIntrinsicBounds(0, 0,  icon, 0)
                        return@OnTouchListener true
                    } else {
                        val text = getString(R.string.incorrect_email)
                        viewBinding.emailTextInput.error = text
                    }
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
        hideKeyboard()
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