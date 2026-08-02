package com.abdullojon.maxwayclone.data.source.remote.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val ls: List<OrderProductItem>,
    val latitude: String,
    val longitude: String,
    val address: String
)
