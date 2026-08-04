package com.abdullojon.maxwayclone.presentation.profile.branch

import androidx.lifecycle.ViewModel
import com.abdullojon.maxwayclone.data.source.remote.dto.response.branches.BranchData
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class BranchesState(
    val isLoading: Boolean = false,
    val branches: List<BranchData> = emptyList(),
    val selectedBranch: BranchData? = null,
    val error: String? = null
)

@HiltViewModel
class BranchesViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<BranchesState, Nothing>, ViewModel() {
    override val container = container<BranchesState, Nothing>(BranchesState())

    init {
        loadBranches()
    }

    fun loadBranches() = intent {
        reduce { state.copy(isLoading = true) }
        repository.getBranches().collectLatest { result ->
            result.onSuccess { list ->
                reduce { state.copy(isLoading = false, branches = list) }
            }.onFailure {
                reduce { state.copy(isLoading = false, error = it.message) }
            }
        }
    }

    fun selectBranch(branch: BranchData) = intent {
        reduce { state.copy(selectedBranch = branch) }
    }
}
