package com.abdullojon.maxwayclone.domain.model

data class ProductsByCategoryUIData(
    val id: Int,
    val name: String,
    val products: List<ProductUIData>
)
