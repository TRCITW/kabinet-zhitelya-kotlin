package com.example.kabinetzhitelya_kotlin.data

import android.content.Context
import com.example.kabinetzhitelya_kotlin.app.App

object PreferenseStorage {

    private val context = App.INSTANCE.applicationContext
    val authPrefs = context.getSharedPreferences("authFile", Context.MODE_PRIVATE)

}