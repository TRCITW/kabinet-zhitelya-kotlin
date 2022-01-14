package com.example.kabinetzhitelya_kotlin.app

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kabinetzhitelya_kotlin.R
import com.example.kabinetzhitelya_kotlin.databinding.ActivityMainBinding
import com.example.kabinetzhitelya_kotlin.ui.Screens
import com.github.terrakok.cicerone.androidx.AppNavigator

class MainActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private val navigator = AppNavigator(this, R.id.fragmentContainerView)
    private val navigatorHolder get() = App.INSTANCE.navigationHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        App.INSTANCE.router.newRootScreen(Screens.AuthScreen())
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