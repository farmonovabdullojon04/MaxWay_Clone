package com.abdullojon.maxwayclone.presentation.basket

import com.abdullojon.maxwayclone.domain.model.ProductUIData

data class BasketState(
    val isLoading: Boolean=false,
    val items: List<ProductUIData> =emptyList()
)