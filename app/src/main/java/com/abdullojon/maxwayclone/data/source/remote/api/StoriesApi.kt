package com.abdullojon.maxwayclone.data.source.remote.api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import retrofit2.Response
import retrofit2.http.GET

interface StoriesApi {
    @GET("stories")
    suspend fun getStories(): Response<MyResponse<Stories>>
}