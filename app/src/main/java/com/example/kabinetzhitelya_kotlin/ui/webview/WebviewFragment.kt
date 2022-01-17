package com.example.kabinetzhitelya_kotlin.ui.webview

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.databinding.FragmentWebViewBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment


class WebviewFragment: BaseFragment(), WebviewView {

    private lateinit var viewBinding: FragmentWebViewBinding
    private val presenter = WebviewPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWebViewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        presenter.bind(this)

        val permissions = arrayOf<String>(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        ActivityCompat.requestPermissions(requireActivity(), permissions, 0)

        configureWebview()
    }

    override fun loadWebview(withCookie: String) {
        val url = getString(R.string.web_view_url)
        CookieManager.getInstance().setCookie(url, withCookie)
        viewBinding.webView.loadUrl(url)
    }

    override fun onDetach() {
        super.onDetach()

        presenter.unbind()
    }

    private fun configureWebview() {
        val settings = viewBinding.webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true

        viewBinding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                val authUrl = getString(R.string.logout_url)
                if (url == authUrl) {
                    presenter.navigateToAuth()
                }
            }
        }

        viewBinding.webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimetype)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Старт загрузки файла...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype))
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, contentDisposition, mimetype)
            )
            val dm = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val pm = App.INSTANCE.appContext
            val hasPerm = pm.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            if (hasPerm) {
                dm.enqueue(request)
            } else {
                Toast.makeText(
                    context,
                    "Для скачивания файлов необходимо выдать приложению доступ на запись в локальное хранилище в настройках",
                    Toast.LENGTH_LONG).show();
            }

        }
    }

}