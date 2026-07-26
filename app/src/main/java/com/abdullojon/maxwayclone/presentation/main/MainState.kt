package com.abdullojon.maxwayclone.presentation.main

import com.abdullojon.maxwayclone.data.remote.dto.response.categories.AllCategories

data class MainState(
    val isLoading: Boolean = false,
    val categories: List<AllCategories> = emptyList(),
    val selectedCategoryId: String? = null,
    val error: String? = null
)
