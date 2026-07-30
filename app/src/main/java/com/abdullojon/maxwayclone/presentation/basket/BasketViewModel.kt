package com.abdullojon.maxwayclone.presentation.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<BasketState, Nothing>, ViewModel() {
    override val container = container<BasketState, Nothing>(BasketState())

    init {
        observeBasket()
    }

    private fun observeBasket() {
        repository.getBasketProducts().onEach { basketItems ->
            intent {
                reduce { state.copy(items = basketItems) }
            }
        }.launchIn(viewModelScope)
    }

    fun updateCount(id: Int, newCount: Int) {
        repository.updateCount(id, newCount)
    }

    fun clearBasket(){
        repository.clearCart()
    }
}
