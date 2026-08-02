package com.abdullojon.maxwayclone.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdullojon.maxwayclone.data.source.remote.dto.response.auth.UserData
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class ProfileState(
    val isLoading: Boolean = false,
    val userData: UserData? = null,
    val error: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AppRepository
) : ContainerHost<ProfileState, Nothing>, ViewModel() {
    override val container = container<ProfileState, Nothing>(ProfileState())

    init {
        loadUserInfo()
        observeUserData()
    }

    private fun observeUserData() {
        repository.userDataFlow.onEach { userData ->
            intent {
                reduce { state.copy(userData = userData) }
            }
        }.launchIn(viewModelScope)
    }

    fun loadUserInfo() = intent {
        reduce { state.copy(isLoading = true) }
        val result = repository.getUserInfo()
        result.onSuccess { data ->
            reduce { state.copy(isLoading = false, userData = data) }
        }.onFailure {
            reduce { state.copy(isLoading = false, error = it.message) }
        }
    }

    fun updateUserInfo(name: String, birthDate: String, onSuccess: () -> Unit) = intent {
        reduce { state.copy(isLoading = true) }
        val result = repository.updateUserInfo(name, birthDate)
        reduce { state.copy(isLoading = false) }
        if (result.isSuccess) {
            loadUserInfo()
            onSuccess()
        } else {
            reduce { state.copy(error = result.exceptionOrNull()?.message) }
        }
    }

    fun logout() {
        repository.logout()
    }

    fun deleteAccount(onSuccess: () -> Unit) = intent {
        reduce { state.copy(isLoading = true) }
        val result = repository.deleteAccount()
        reduce { state.copy(isLoading = false) }
        if (result.isSuccess) {
            onSuccess()
        } else {
            reduce { state.copy(error = result.exceptionOrNull()?.message) }
        }
    }
}
