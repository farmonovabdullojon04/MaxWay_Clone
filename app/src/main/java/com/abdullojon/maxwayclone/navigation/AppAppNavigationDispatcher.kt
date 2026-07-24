package com.abdullojon.maxwayclone.navigation

import androidx.lifecycle.MutableLiveData
import cafe.adriel.voyager.core.screen.Screen

object AppAppNavigationDispatcher: AppNavigator, AppNavigationHandler {

    override val backStack = MutableLiveData<AppNavigationParam>()

    private fun navigate(param: AppNavigationParam) {
        backStack.postValue(param)
    }

    override fun navigateTo(screen: Screen) = navigate {
        push(screen)
    }

    override fun back() = navigate {
        pop()
    }

    override fun replace(screen: Screen) = navigate {
        replace(screen)
    }

    override fun replaceAll(screen: Screen) = navigate {
        replaceAll(screen)
    }
}
