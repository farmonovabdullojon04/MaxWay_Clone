package com.abdullojon.maxwayclone.data.source.remote.dto.response.auth

data class AuthResponse<T>(
    val message: String,
    val data: T
)

data class TokenResponse(
    val token: String
)
