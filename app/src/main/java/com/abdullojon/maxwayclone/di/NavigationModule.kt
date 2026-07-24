package com.abdullojon.maxwayclone.di

import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.navigation.AppNavigationHandler
import com.abdullojon.maxwayclone.navigation.AppNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideAppNavigation(): AppNavigator = AppAppNavigationDispatcher

    @Provides
    @Singleton
    fun provideAppNavigationHandler(): AppNavigationHandler = AppAppNavigationDispatcher
}
