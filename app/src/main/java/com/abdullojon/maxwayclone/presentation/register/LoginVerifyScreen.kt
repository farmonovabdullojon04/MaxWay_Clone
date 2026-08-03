package com.abdullojon.maxwayclone.presentation.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.basket.BasketScreen

class LoginVerifyScreen(private val phone: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = hiltViewModel()
        val state by viewModel.container.stateFlow.collectAsState()

        LaunchedEffect(phone) {
            viewModel.onPhoneChange(phone)
        }

        AuthBaseScreen(
            title = "Код подтверждения",
            buttonText = "Продолжить",
            onButtonClick = {
                viewModel.verifyCode { isNewUser->
                    if (isNewUser){
                        AppAppNavigationDispatcher.navigateTo(LoginNameScreen())
                    }else{
                        AppAppNavigationDispatcher.navigateTo(BasketScreen())
                    }
                }
            }
        ) {
            OutlinedTextField(
                value = state.code,
                onValueChange = viewModel::onCodeChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("1 1 1 1",
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center) },
                textStyle = androidx.compose.ui.text.TextStyle(textAlign = TextAlign.Center)
            )
        }
    }
}