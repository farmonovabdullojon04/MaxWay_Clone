package com.abdullojon.maxwayclone.data.source.local.preference

import android.content.Context
import javax.inject.Inject

class Prefs @Inject constructor(context: Context){
    private val prefs=context.getSharedPreferences("maxway_prefs", Context.MODE_PRIVATE)

    var token: String?
        get() = prefs.getString("token",null)
        set(value) = prefs.edit().putString("token",value).apply()

    var isLoggedIn: Boolean
        get() = prefs.getBoolean("is_logged_in",false)
        set(value) = prefs.edit().putBoolean("is_logged_in",value).apply()

    fun clearUser() {
        prefs.edit().clear().apply()
    }
}