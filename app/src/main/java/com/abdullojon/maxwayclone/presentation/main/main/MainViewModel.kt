package com.abdullojon.maxwayclone.presentation.main.main

import androidx.lifecycle.ViewModel
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
 import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<MainState, Nothing>, ViewModel() {
    override val container = container<MainState, Nothing>(MainState())
    init {
        loadCategories()
        loadProducts()
    }
    fun loadCategories() = intent {
        reduce { state.copy(isLoading = true) }
        repository.getAllCategories().collectLatest { result ->
            result.onSuccess { list ->
                reduce {
                    state.copy(
                        isLoading = false,
                        categories = list,
                        selectedCategoryId = list.firstOrNull()?.id?.toString(),
                        error = null
                    )
                }
            }.onFailure { throwable ->
                reduce { state.copy(isLoading = false, error = throwable.message) }
            }
        }
    }

    fun loadProducts()=intent{
        reduce { state.copy(isLoading = true) }
        repository.getProducts().collectLatest { result ->
            result.onSuccess { list->
                reduce {
                    state.copy(
                        isLoading = false,
                        allProducts = list,
                        filteredProducts = list,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                reduce { state.copy(isLoading = false,error=throwable.message) }
            }
        }
    }
    fun selectCategory(id: String) = intent {
        reduce {
            val newState=state.copy(selectedCategoryId = id)
            val filtered=if (id=="all"){
                newState.allProducts
            }else{
                newState.allProducts.filter { it.categoryID.toString()==id }
            }
            newState.copy(filteredProducts = filtered)
        }
    }

    fun selectProduct(id: String)=intent{
        reduce { state.copy(selectedCategoryId = id) }
    }

}
