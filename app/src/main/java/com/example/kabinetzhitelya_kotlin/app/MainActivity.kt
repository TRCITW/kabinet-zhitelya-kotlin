package com.example.kabinetzhitelya_kotlin.app

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.data.repositories.AuthRepository
import com.example.kabinetzhitelya_kotlin.databinding.ActivityMainBinding
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.fragmentContainerView)
    private val navigatorHolder get() = App.INSTANCE.navigationHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            val token = task.result
            AuthRepository.saveFcmToken(token)
        })

        val cookie = AuthRepository.getCookie()
        if (cookie == "") {
            App.INSTANCE.router.newRootScreen(Screens.AuthScreen())
        } else {
            handleDeeplink()
        }
    }

    private fun handleDeeplink() {

        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                if (pendingDynamicLinkData != null) {
                    var deeplink = pendingDynamicLinkData.link
                    App.INSTANCE.router.newRootScreen(Screens.WebviewScreen(deeplink.toString()))
                }
                App.INSTANCE.router.newRootScreen(Screens.WebviewScreen(null))
            }
            .addOnFailureListener(this) { error ->
                App.INSTANCE.router.newRootScreen(Screens.WebviewScreen(null))
            }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

}