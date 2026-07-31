package com.abdullojon.maxwayclone.presentation.register

import androidx.lifecycle.ViewModel
import com.abdullojon.maxwayclone.domain.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AppRepository
): ContainerHost<RegisterState, Nothing>, ViewModel(){
    override val container = container<RegisterState, Nothing>(RegisterState())

    fun onPhoneChange(phone: String)=intent{
        reduce { state.copy(phone=phone) }
    }
    fun onCodeChange(code: String)=intent{
        reduce { state.copy(code=code) }
    }
    fun onNameChange(name: String)=intent{
        reduce { state.copy(name=name) }
    }

    fun sendCode(onSuccess:()-> Unit)=intent{
        reduce { state.copy(isLoading = true) }
        val result=repository.register(state.phone)
        reduce { state.copy(isLoading = false) }
        if (result.isSuccess) onSuccess()
    }

    fun verifyCode(onSuccess: () -> Unit)=intent{
        reduce { state.copy(isLoading = true) }
        val result=repository.verify(state.phone,state.code.toIntOrNull()?:0)
        reduce { state.copy(isLoading = false) }
        if (result.isSuccess) onSuccess()
    }

}
