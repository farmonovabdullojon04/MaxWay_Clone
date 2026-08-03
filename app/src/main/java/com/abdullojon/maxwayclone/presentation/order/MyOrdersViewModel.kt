package com.abdullojon.maxwayclone.presentation.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullojon.maxwayclone.data.source.remote.dto.response.order.OrderData
import com.abdullojon.maxwayclone.domain.model.OrderStatus
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<MyOrdersState, Nothing>, ViewModel() {
    override val container = container<MyOrdersState, Nothing>(MyOrdersState())

    init {
        loadOrders()
        observeNewOrders()
    }

    private fun observeNewOrders() {
        viewModelScope.launch {
            repository.newOrderFlow.collectLatest { newOrder ->
                intent {
                    reduce {
                        state.copy(
                            currentOrders = listOf(newOrder),
                            activeOrder = newOrder,
                            currentStatus = OrderStatus.ORDERED
                        )
                    }
                    startStatusSimulation()
                }
            }
        }
    }
    fun loadOrders() = intent {
        reduce { state.copy(isLoading = true) }
        repository.getMyOrders().collect { result ->
            result.onSuccess { allOrders ->
                reduce {
                    state.copy(
                        isLoading = false,
                        historyOrders = allOrders,
                    )
                }
            }.onFailure {
                reduce { state.copy(isLoading = false) }
            }
        }
    }

    private fun startStatusSimulation() = intent {
        val allStatuses = OrderStatus.entries.toTypedArray()
        for (status in allStatuses) {
            reduce { state.copy(currentStatus = status) }
            
            if (status == OrderStatus.DELIVERED) {
                delay(3000)
                val updatedCurrent = state.currentOrders.toMutableList()
                val movingOrder = updatedCurrent.removeFirstOrNull()
                val updatedHistory = state.historyOrders.toMutableList()
                movingOrder?.let { updatedHistory.add(0, it) }
                
                reduce { 
                    state.copy(
                        currentOrders = updatedCurrent,
                        historyOrders = updatedHistory,
                        activeOrder = null 
                    ) 
                }
                break
            }
            delay(5000)
        }
    }

    fun selectOrder(order: OrderData) = intent {
        reduce { state.copy(activeOrder = order, showDetails = true) }
    }

    fun toggleOrderDetails() = intent {
        reduce { state.copy(showDetails = !state.showDetails) }
    }
}
