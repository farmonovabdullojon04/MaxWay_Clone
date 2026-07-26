package com.abdullojon.maxwayclone.domain.repository

import com.abdullojon.maxwayclone.data.remote.dto.response.categories.AllCategories
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllCategories(): Flow<Result<List<AllCategories>>>
}