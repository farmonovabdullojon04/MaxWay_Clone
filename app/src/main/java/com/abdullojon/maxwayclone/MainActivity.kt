package com.abdullojon.maxwayclone

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.abdullojon.maxwayclone.data.source.local.preference.Prefs
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.main.main.MainScreen
import com.abdullojon.maxwayclone.util.LocaleHelper
import com.abdullojon.maxwayclone.ui.theme.MaxWayCloneTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context) {
        val prefs = Prefs(newBase)
        super.attachBaseContext(LocaleHelper.setLocale(newBase, prefs.language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MaxWayCloneTheme {
                Navigator(screen = MainScreen()) { navigator ->
                    LaunchedEffect(Unit) {
                        AppAppNavigationDispatcher.backStack.observe(this@MainActivity) { 
                            it(navigator) 
                        }
                    }
                    CurrentScreen()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),101)
        }
    }
}

