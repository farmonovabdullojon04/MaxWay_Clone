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

        if (result.isSuccess){
            reduce { state.copy(isLoading = false) }
            onSuccess()
        }else{
            val repeatResult=repository.repeat(state.phone)
            reduce { state.copy(isLoading = false) }
            if (repeatResult.isSuccess){
                onSuccess()
            }
        }
    }

    fun verifyCode(onSuccess: (isNewUser: Boolean) -> Unit)=intent{
        reduce { state.copy(isLoading = true) }

        val result=repository.verify(state.phone,state.code.toIntOrNull()?:0)

        if (result.isSuccess){
            val infoResult=repository.getUserInfo()

            reduce { state.copy(isLoading = false) }

            if (infoResult.isSuccess){
                val user=infoResult.getOrNull()
                val isNewUser=user?.name.isNullOrBlank()
                onSuccess(isNewUser)
            }else{
                onSuccess(true)
            }
        }else{
            reduce { state.copy(isLoading = false) }
        }
    }

    fun updateNameAndFinish(onSuccess: () -> Unit) = intent {
        reduce { state.copy(isLoading = true) }
        repository.updateUserInfo(state.name, "")
        reduce { state.copy(isLoading = false) }
        onSuccess()
    }

}

