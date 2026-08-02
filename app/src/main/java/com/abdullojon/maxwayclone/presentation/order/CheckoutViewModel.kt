package com.abdullojon.maxwayclone.presentation.order

import androidx.lifecycle.ViewModel
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class CheckoutState(
    val isLoading: Boolean = false,
    val address: String = "TEST",
    val latitude: String = "41.0",
    val longitude: String = "69.0",
    val error: String? = null
)

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<CheckoutState, Nothing>, ViewModel() {
    override val container = container<CheckoutState, Nothing>(CheckoutState())

    fun createOrder(onSuccess: () -> Unit) = intent {
        reduce { state.copy(isLoading = true) }
        val result = repository.createOrder(
            latitude = state.latitude,
            longitude = state.longitude,
            address = state.address
        )
        reduce { state.copy(isLoading = false) }
        if (result.isSuccess) {
            repository.clearCart()
            onSuccess()
        } else {
            reduce { state.copy(error = result.exceptionOrNull()?.message) }
        }
    }
}
