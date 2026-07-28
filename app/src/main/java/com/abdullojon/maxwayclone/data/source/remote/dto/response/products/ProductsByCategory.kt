package com.abdullojon.maxwayclone.data.source.remote.dto.response.products

data class ProductsByCategory(
    val id: Int,
    val name: String,
    val products: List<Product>
)