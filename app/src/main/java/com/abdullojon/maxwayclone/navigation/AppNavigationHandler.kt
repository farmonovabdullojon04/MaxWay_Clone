package com.abdullojon.maxwayclone.navigation

import androidx.lifecycle.LiveData

interface AppNavigationHandler {
    val backStack: LiveData<AppNavigationParam>
}