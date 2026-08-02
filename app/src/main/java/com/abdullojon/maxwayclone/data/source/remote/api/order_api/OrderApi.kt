package com.abdullojon.maxwayclone.data.source.remote.api.order_api

import com.abdullojon.maxwayclone.data.source.remote.dto.request.OrderRequest
import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.AuthResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.order.OrderData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderApi {
    @POST("create_order")
    suspend fun createOrder(
        @Header("token") token: String,
        @Body request: OrderRequest
    ): Response<AuthResponse<OrderData>>

    @GET("my_orders")
    suspend fun getMyOrders(
        @Header("token") token: String
    ): Response<MyResponse<OrderData>>
}