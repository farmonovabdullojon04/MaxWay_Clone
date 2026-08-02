package com.abdullojon.maxwayclone.data.source.remote.dto.response.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderData(
    val id: Int,
    val userID: Int,
    val ls: List<OrderProductResponse>,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val createTime: Long,
    val sum: Int
)