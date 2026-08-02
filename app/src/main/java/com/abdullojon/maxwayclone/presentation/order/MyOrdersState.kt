package com.abdullojon.maxwayclone.presentation.order

import com.abdullojon.maxwayclone.data.source.remote.dto.response.order.OrderData
import com.abdullojon.maxwayclone.domain.model.OrderStatus

data class MyOrdersState(
    val isLoading: Boolean = false,
    val currentOrders: List<OrderData> = emptyList(),
    val historyOrders: List<OrderData> = emptyList(),
    val activeOrder: OrderData? = null,
    val currentStatus: OrderStatus = OrderStatus.ORDERED,
    val showDetails: Boolean = false
)
