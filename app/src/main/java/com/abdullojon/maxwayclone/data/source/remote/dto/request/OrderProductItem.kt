package com.abdullojon.maxwayclone.data.source.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class OrderProductItem(
    val productID: Int,
    val count: Int
)
