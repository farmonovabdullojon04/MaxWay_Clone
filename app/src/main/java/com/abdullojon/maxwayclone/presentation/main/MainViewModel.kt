package com.abdullojon.maxwayclone.presentation.main

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

    fun selectCategory(id: String) = intent {
        reduce { state.copy(selectedCategoryId = id) }
    }
}
