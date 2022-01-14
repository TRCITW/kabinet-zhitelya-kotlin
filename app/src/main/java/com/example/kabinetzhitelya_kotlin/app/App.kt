package com.example.kabinetzhitelya_kotlin.app

import android.app.Application
import android.content.Context
import com.github.terrakok.cicerone.Cicerone

class App: Application() {

    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigationHolder get() = cicerone.getNavigatorHolder()
    lateinit var appContext: Context

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appContext = applicationContext
    }
}