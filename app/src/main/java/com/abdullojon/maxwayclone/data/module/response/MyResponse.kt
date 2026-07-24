package com.abdullojon.maxwayclone.data.module.response

import com.abdullojon.maxwayclone.data.module.response.products.Product

data class MyResponse<T>(
    val message: String,
    val data: List<T>
)