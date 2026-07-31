package com.abdullojon.maxwayclone.presentation.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class LoginPhoneScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = hiltViewModel()
        val state by viewModel .container.stateFlow.collectAsState()

        AuthBaseScreen(
            title = "Номер телефона",
            buttonText = "Продолжить",
            onButtonClick = {
                viewModel.sendCode { 
                    AppAppNavigationDispatcher.navigateTo(LoginVerifyScreen(state.phone)) 
                }
            }
        ) {
            OutlinedTextField(
                value = state.phone,
                onValueChange = viewModel::onPhoneChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите номер телефона") },
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}