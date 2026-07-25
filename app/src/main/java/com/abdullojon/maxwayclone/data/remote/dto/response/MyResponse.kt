package com.abdullojon.maxwayclone.data.remote.dto.response

data class MyResponse<T>(
    val message: String,
    val data: List<T>
)