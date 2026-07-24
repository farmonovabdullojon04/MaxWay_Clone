package com.abdullojon.maxwayclone.data.module.api

import com.abdullojon.maxwayclone.data.module.response.MyResponse
import com.abdullojon.maxwayclone.data.module.response.products.Product
import com.abdullojon.maxwayclone.data.module.response.products.ProductsByCategory
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<Result<MyResponse<Product>>>

    @GET("products_by_category")
    suspend fun getProductsByCategory(): Response<Result<MyResponse<ProductsByCategory>>>
}