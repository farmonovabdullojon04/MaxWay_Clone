package com.abdullojon.maxwayclone.domain.repository

import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.ProductsByCategory
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllCategories(): Flow<Result<List<AllCategories>>>
    fun getProducts(): Flow<Result<List<Product>>>
    fun getProductsByCategory(): Flow<Result<List<ProductsByCategory>>>
    fun getAds(): Flow<Result<List<Ads>>>
    fun getStories(): Flow<Result<List<Stories>>>
}