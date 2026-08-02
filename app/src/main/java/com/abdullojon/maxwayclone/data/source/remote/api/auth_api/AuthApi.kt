package com.abdullojon.maxwayclone.data.source.remote.api.auth_api

import com.abdullojon.maxwayclone.data.source.remote.dto.request.RegisterRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.RepeatRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.UpdateUserRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.request.VerifyRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.AuthResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.TokenResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.UserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE

interface AuthApi {
    @POST("register")
    suspend fun register(@Body body: RegisterRequest): Response<AuthResponse<Any>>

    @POST("verify")
    suspend fun verify(@Body body: VerifyRequest): Response<AuthResponse<TokenResponse>>

    @POST("repeat")
    suspend fun repeat(@Body body: RepeatRequest): Response<AuthResponse<Any>>

    @GET("user_info")
    suspend fun getUserInfo(@Header("token") token: String): Response<AuthResponse<UserData>>

    @PUT("update_user_info")
    suspend fun updateUserInfo(
        @Header("token") token: String,
        @Body body: UpdateUserRequest
    ): Response<AuthResponse<Any>>

    @DELETE("delete_account")
    suspend fun deleteAccount(@Header("token") token: String): Response<AuthResponse<Any>>
}
