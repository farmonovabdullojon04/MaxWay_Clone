package com.abdullojon.maxwayclone.data.source.remote.dto.response.order

import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product
import kotlinx.serialization.Serializable

@Serializable
data class OrderProductResponse(
    val count: Int,
    val productData: Product
)
