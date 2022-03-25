package com.example.kabinetzhitelya_kotlin.ui.scan_qr

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.databinding.FragmentQrScanBinding
import com.example.kabinetzhitelya_kotlin.ui.auth.AuthView
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment

class ScanQRFragment: BaseFragment(), ScanQRView {

    private lateinit var viewBinding: FragmentQrScanBinding
    private lateinit var scanner: CodeScanner
    private val presenter = ScanQRPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentQrScanBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onStart() {
        super.onStart()

        presenter.bind(this)
        viewBinding.progressBar2.visibility = View.GONE

        val activity = requireActivity()
        scanner = CodeScanner(activity, viewBinding.scannerView)
        scanner.decodeCallback = DecodeCallback { res ->
            activity.runOnUiThread {
                presenter.sendCode(res.text)
            }
        }
        scanner.errorCallback = ErrorCallback {
            activity.runOnUiThread {
                showError(it.localizedMessage)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val permissions = arrayOf<String>(
            android.Manifest.permission.CAMERA
        )

        if (ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            scanner.startPreview()
            viewBinding.scannerView.setOnClickListener {
                scanner.startPreview()
            }
        } else {
            val text = getText(R.string.camera_permission_required).toString()
            showError(text)
            ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
        }
    }

    override fun onPause() {
        scanner.releaseResources()
        super.onPause()
    }

    override fun onDestroy() {
        presenter.unbind()
        super.onDestroy()
    }

    override fun updateState(state: ScanQRView.State) {
        viewBinding.progressBar2.visibility = View.GONE
        when (state) {
            ScanQRView.State.LOADING -> {
                viewBinding.progressBar2.visibility = View.VISIBLE
            }
            ScanQRView.State.SUCCESS -> {
            }
            ScanQRView.State.ERROR -> {
            }
        }
    }

    override fun openBankURL(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

}