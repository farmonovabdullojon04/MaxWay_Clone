package com.abdullojon.maxwayclone.di

import android.content.Context
import com.abdullojon.maxwayclone.BuildConfig.BASE_URL
import com.abdullojon.maxwayclone.data.source.local.preference.Prefs
import com.abdullojon.maxwayclone.data.source.remote.api.auth_api.AuthApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.AdsApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.CategoriesApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.ProductsApi
import com.abdullojon.maxwayclone.data.source.remote.api.main_api.StoriesApi
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @[Provides Singleton]
    fun okHttpClient(@ApplicationContext context: Context): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor.Builder(context).build())
        .build()
    @[Provides Singleton]
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @[Provides Singleton]
    fun providesCategoriesApi(retrofit: Retrofit): CategoriesApi = retrofit.create<CategoriesApi>()

    @[Provides Singleton]
    fun providesProductsApi(retrofit: Retrofit): ProductsApi = retrofit.create<ProductsApi>()

    @[Provides Singleton]
    fun providesAdsApi(retrofit: Retrofit): AdsApi = retrofit.create<AdsApi>()

    @[Provides Singleton]
    fun providesStoriesApi(retrofit: Retrofit): StoriesApi=retrofit.create<StoriesApi>()

    @[Provides Singleton]
    fun providesGson()= Gson()

    @Provides
    @Singleton
    fun providesAuthApi(retrofit: Retrofit): AuthApi=retrofit.create<AuthApi>()

    @Provides
    @Singleton
    fun providesPrefs(@ApplicationContext context: Context): Prefs= Prefs(context)

}