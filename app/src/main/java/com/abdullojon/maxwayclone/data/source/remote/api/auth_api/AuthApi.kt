package com.abdullojon.maxwayclone.data.source.remote.api.auth_api

import com.abdullojon.maxwayclone.data.source.remote.dto.request.RegisterRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RepeatRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.VerifyRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.AuthResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse<Any>>

    @POST("verify")
    suspend fun verify(@Body body: VerifyRequest): Response<AuthResponse<TokenResponse>>

    @POST("repeat")
    suspend fun repeat(@Body body: RepeatRequest): Response<AuthResponse<Any>>
}
