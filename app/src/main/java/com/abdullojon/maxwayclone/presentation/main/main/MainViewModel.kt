package com.abdullojon.maxwayclone.presentation.main.main

import androidx.lifecycle.ViewModel
import com.abdullojon.maxwayclone.data.source.remote.dto.response.categories.AllCategories
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
        loadMainData()
        loadAds()
        loadStories()
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

    fun loadMainData()=intent{
        reduce {state.copy(isLoading = true) }
        repository.getProductsByCategory().collectLatest { result ->
            result.onSuccess { listByCategory->
                val categories=listByCategory.map {
                    AllCategories(id = it.id, name = it.name)
                }
                val allProducts=listByCategory.flatMap { it.products }
                val firstCatId=categories.firstOrNull()?.id?.toString()
                val initialFiltered=if(firstCatId!=null){
                    allProducts.filter { it.categoryID.toString()==firstCatId }
                }else allProducts
                reduce {
                    state.copy(
                        isLoading = false,
                        categories=categories,
                        allProducts=allProducts,
                        filteredProducts = initialFiltered,
                        selectedProductId = firstCatId,
                        error = null
                    )
                }
            }.onFailure { throwable ->
                reduce { state.copy(isLoading = false,error=throwable.message) }
            }
        }
    }
    fun loadAds()=intent{
        repository.getAds().collectLatest { result ->
            result.onSuccess { list->
                reduce { state.copy(ads = list) }
            }
        }
    }
    fun loadStories()=intent{
        repository.getStories().collectLatest { result ->
            result.onSuccess { list->
                reduce { state.copy(stories = list) }
            }
        }
    }
}
