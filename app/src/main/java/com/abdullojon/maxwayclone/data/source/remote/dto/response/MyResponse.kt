package com.abdullojon.maxwayclone.data.source.remote.dto.response

data class MyResponse<T>(
    val message: String,
    val data: List<T>
)