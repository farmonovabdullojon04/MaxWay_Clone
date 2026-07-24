package com.abdullojon.maxwayclone.data.module.api

import com.abdullojon.maxwayclone.data.module.response.MyResponse
import com.abdullojon.maxwayclone.data.module.response.categories.AllCategories
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApi {
    @GET("categories")
    suspend fun getAllCategory(): Response<Result<MyResponse<AllCategories>>>
}