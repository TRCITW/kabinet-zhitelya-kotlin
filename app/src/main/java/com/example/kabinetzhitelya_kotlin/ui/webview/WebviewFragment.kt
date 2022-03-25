package com.example.kabinetzhitelya_kotlin.ui.webview

import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
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
import androidx.core.os.bundleOf
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.app.App
import com.example.kabinetzhitelya_kotlin.databinding.FragmentWebViewBinding
import com.example.kabinetzhitelya_kotlin.ui.base.fragment.BaseFragment


class WebviewFragment: BaseFragment(), WebviewView {

    private lateinit var viewBinding: FragmentWebViewBinding
    private val presenter = WebviewPresenter()
    private var cookies: String? = null

    companion object {
        const val LINK = "link"

        fun newInstance(link: String?): WebviewFragment {
            val fragment = WebviewFragment()
            fragment.arguments = bundleOf(
                LINK to link
            )
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentWebViewBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureWebview()
        presenter.bind(this)

        val permissions = arrayOf<String>(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        ActivityCompat.requestPermissions(requireActivity(), permissions, 0)
    }

    override fun loadWebview(withCookie: String) {
        cookies = withCookie
        val url = getString(R.string.web_view_url)
        CookieManager.getInstance().setCookie(url, withCookie)

        val bundle = requireArguments()
        val uri = bundle.getString(LINK)

        if (uri == "/" || uri == null) {
            viewBinding.webView.loadUrl(url)
            return
        } else {
            CookieManager.getInstance().setCookie(uri, withCookie)
        }

        if (uri?.contains("tickets")) {
            val url = getString(R.string.tickets)
            viewBinding.webView.loadUrl(url)
        } else if (uri?.contains("requests")) {
            val url = getString(R.string.requests)
            viewBinding.webView.loadUrl(url)
        } else if (uri?.contains("accruals")) {
            val url = getString(R.string.accruals)
            viewBinding.webView.loadUrl(url)
        } else if (uri?.contains("meters")) {
            val url = getString(R.string.counters)
            viewBinding.webView.loadUrl(url)
        } else if (uri?.contains("news")) {
            val url = getString(R.string.news)
            viewBinding.webView.loadUrl(url)
        }
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
                if (url == null) return
//                val authUrl = getString(R.string.logout_url)
//                val authUrl = "https://lk2.eis24.me/#/auth/login/"
                if (url.contains("auth")) {
                    presenter.navigateToAuth()
                } else if (url.contains("arseniy")) {
                    val mainUrl = getString(R.string.web_view_url)
                    viewBinding.webView.loadUrl(mainUrl)

                    val url = getString(R.string.telegram_bot_url)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }

        viewBinding.webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val cookie = CookieManager.getInstance().getCookie(url)
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimetype)
            request.addRequestHeader("cookie", cookie);
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
                Toast.makeText(context,
                    "Старт загрузки файла...",
                    Toast.LENGTH_LONG).show()

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