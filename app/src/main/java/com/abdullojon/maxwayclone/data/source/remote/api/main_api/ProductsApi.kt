package com.abdullojon.maxwayclone.data.source.remote.api.main_api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.ProductsByCategory
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<MyResponse<Product>>

    @GET("products_by_category")
    suspend fun getProductsByCategory(): Response<MyResponse<ProductsByCategory>>

    @GET("products_by_query")
    suspend fun searchProducts(@Query("query") text: String): Response<MyResponse<Product>>
}