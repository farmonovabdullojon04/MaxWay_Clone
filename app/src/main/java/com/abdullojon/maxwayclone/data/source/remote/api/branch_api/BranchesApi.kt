package com.abdullojon.maxwayclone.data.source.remote.api.branch_api

import com.abdullojon.maxwayclone.data.source.remote.dto.response.MyResponse
import com.abdullojon.maxwayclone.data.source.remote.dto.response.branches.BranchData
import retrofit2.Response
import retrofit2.http.GET

interface BranchesApi {
    @GET("branches")
    suspend fun getBranches(): Response<MyResponse<BranchData>>
}