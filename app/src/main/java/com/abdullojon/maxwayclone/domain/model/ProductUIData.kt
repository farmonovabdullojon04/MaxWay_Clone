package com.abdullojon.maxwayclone.domain.model

data class ProductUIData(
    val id: Int,
    val categoryID: Int,
    val name: String,
    val description: String,
    val image: String,
    val cost: Int,
    val count:Int=0
)