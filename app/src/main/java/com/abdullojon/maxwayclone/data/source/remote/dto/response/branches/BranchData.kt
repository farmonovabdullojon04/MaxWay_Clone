package com.abdullojon.maxwayclone.data.source.remote.dto.response.branches

import kotlinx.serialization.Serializable

@Serializable
data class
BranchData(
    val id: Int,
    val name: String,
    val address: String,
    val phone: String,
    val openTime: String,
    val closeTime: String,
    val latitude: Double,
    val longitude: Double
)
