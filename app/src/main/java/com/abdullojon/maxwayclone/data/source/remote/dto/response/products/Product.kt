package com.abdullojon.maxwayclone.data.source.remote.dto.response.products

data class Product(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int
)