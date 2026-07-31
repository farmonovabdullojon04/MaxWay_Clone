package com.abdullojon.maxwayclone.data.source.remote.api.main_api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApi {
    @GET("categories")
    suspend fun getAllCategory(): Response<MyResponse<AllCategories>>
}