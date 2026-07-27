package com.abdullojon.maxwayclone.presentation.main.main

import com.abdullojon.maxwayclone.data.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.data.remote.dto.response.products.Product

data class MainState(
    val isLoading: Boolean = false,
    val categories: List<AllCategories> = emptyList(),
    val selectedCategoryId: String? = null,
    val error: String? = null,
    val allProducts: List<Product> =emptyList(),
    val filteredProducts: List<Product> =emptyList(),
    val products: List<Product> =emptyList(),
    val selectedProductId:String?=null
)
