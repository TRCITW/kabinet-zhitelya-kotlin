package com.example.kabinetzhitelya_kotlin.ui.`auth-mvvm`

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.ViewModelProvider
import com.example.kabinetzhitelya_kotlin.databinding.FragmentAuthBinding
import com.example.kabinetzhitelya_kotlin.databinding.FragmentQrScanBinding
import com.example.kabinetzhitelya_kotlin.ui.auth.AuthView
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.showError
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class AuthMvvmFragment: BaseFragment() {

    private lateinit var viewBinding: FragmentAuthBinding
//    private val viewModel: AuthMvvmViewModel by viewModels()
    private val compositeDisposable = CompositeDisposable()

    companion object {
        fun newInstance(): BaseFragment {
            return BaseFragment()
        }
    }

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

        subData()
    }

    private fun subData() {

//        viewModel.loadingState.subscribeBy(
//            onNext = { state ->
//                viewBinding.progressBar.visibility = state
//            }
//        ).addTo(compositeDisposable)
//
//        viewModel.errorMessages.subscribeBy(
//            onNext = { error ->
//                showError(error)
//            }
//        ).addTo(compositeDisposable)

    }

    override fun onStart() {
        super.onStart()


    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }


}