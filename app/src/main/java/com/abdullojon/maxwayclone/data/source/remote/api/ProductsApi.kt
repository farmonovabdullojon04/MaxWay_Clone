package com.abdullojon.maxwayclone.data.source.remote.api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.Product
import com.abdullojon.maxwayclone.data.source.remote.dto.response.products.ProductsByCategory
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<MyResponse<Product>>

    @GET("products_by_category")
    suspend fun getProductsByCategory(): Response<MyResponse<ProductsByCategory>>
}