package com.abdullojon.maxwayclone.di

import com.abdullojon.maxwayclone.data.repository.AppRepositoryImpl
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository
}