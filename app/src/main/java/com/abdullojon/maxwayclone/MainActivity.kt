package com.abdullojon.maxwayclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.main.main.MainScreen
import com.abdullojon.maxwayclone.ui.theme.MaxWayCloneTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    }
}

