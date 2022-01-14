package com.example.kabinetzhitelya_kotlin.ui.base.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment(), ErrorHandler {

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}