package com.abdullojon.maxwayclone.data.source.remote.api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads
import retrofit2.Response
import retrofit2.http.GET

interface AdsApi{
    @GET("ads")
    suspend fun getAds(): Response<MyResponse<Ads>>
}