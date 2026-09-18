package com.abdullojon.maxwayclone.data.source.remote.dto.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse<T>(
    val message: String,
    val data: T
)

@Serializable
data class RegisterResponse(
    val phone: String,
    val code: Int
)

@Serializable
data class TokenResponse(
    val token: String
)

@Serializable
data class UserData(
    val id: Int,
    val phone: String,
    val token: String,
    val name: String? = null,
    val surname: String? = null,
    val birthDate: String? = null
)
