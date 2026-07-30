package com.abdullojon.maxwayclone.presentation.main.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<MainState, Nothing>, ViewModel() {
    override val container = container<MainState, Nothing>(MainState())
    init {
        loadMainData()
        loadAds()
        loadStories()
        observeCartChanges()
    }

    private fun observeCartChanges() {
        repository.cartFlow.onEach {
            loadMainData()
        }.launchIn(viewModelScope)
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

    fun loadMainData() = intent {
        reduce { state.copy(isLoading = true) }
        repository.getProductsByCategory().collectLatest { result ->
            if (result.isSuccess) {
                val listByCategory = result.getOrNull() ?: emptyList()
                val categories = listByCategory.map {
                    AllCategories(id = it.id, name = it.name)
                }
                val allProducts = listByCategory.flatMap { it.products }
                val currentSelectedId = state.selectedCategoryId ?: categories.firstOrNull()?.id?.toString()
                val filtered = if (currentSelectedId != null) {
                    allProducts.filter { it.categoryID.toString() == currentSelectedId }
                } else allProducts
                
                reduce {
                    state.copy(
                        isLoading = false,
                        categories = categories,
                        allProducts = allProducts,
                        filteredProducts = filtered,
                        selectedCategoryId = currentSelectedId,
                        error = null
                    )
                }
            } else {
                val throwable = result.exceptionOrNull()
                reduce { state.copy(isLoading = false, error = throwable?.message) }
            }
        }
    }

    fun loadAds() = intent {
        repository.getAds().collectLatest { result ->
            if (result.isSuccess) {
                val list = result.getOrNull() ?: emptyList()
                reduce { state.copy(ads = list) }
            }
        }
    }

    fun loadStories() = intent {
        repository.getStories().collectLatest { result ->
            if (result.isSuccess) {
                val list = result.getOrNull() ?: emptyList()
                reduce { state.copy(stories = list) }
            }
        }
    }
    fun updateProductCount(id: Int,newCount: Int)=intent{
        repository.updateCount(id,newCount)
        loadMainData()
    }

    fun onSearchQueryChange(newQuery: String) = intent {
        reduce { state.copy(searchQuery = newQuery, isSearching = newQuery.isNotEmpty()) }
        if (newQuery.isBlank()) {
            loadMainData()
            return@intent
        }
        repository.searchProducts(newQuery).collectLatest { result ->
            if (result.isSuccess) {
                val list = result.getOrNull() ?: emptyList()
                reduce { state.copy(filteredProducts = list) }
            }
        }
    }

    fun onSearchCancel() = intent {
        reduce { state.copy(searchQuery = "", isSearching = false) }
        loadMainData()
    }
}
