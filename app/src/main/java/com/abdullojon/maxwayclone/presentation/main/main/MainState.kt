package com.abdullojon.maxwayclone.presentation.main.main

import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product

data class MainState(
    val isLoading: Boolean = false,
    val categories: List<AllCategories> = emptyList(),
    val selectedCategoryId: String? = null,
    val error: String? = null,
    val allProducts: List<Product> =emptyList(),
    val filteredProducts: List<Product> =emptyList(),
    val products: List<Product> =emptyList(),
    val selectedProductId:String?=null,
    val ads: List<Ads> =emptyList(),
    val stories: List<Stories> =emptyList()
)
