package com.abdullojon.maxwayclone.presentation.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.basket.BasketScreen

class LoginNameScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel: RegisterViewModel = hiltViewModel()
        val state by viewModel.container.stateFlow.collectAsState()
        AuthBaseScreen(
            title = "Имя фамилия",
            buttonText = "Продолжить",
            onButtonClick = {
                viewModel.updateNameAndFinish {
                    AppAppNavigationDispatcher.navigateTo(BasketScreen())
                }
            }
        ) {
            Text("Имя Фамилия", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = state.name,
                onValueChange = viewModel::onNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Введите ваш имя") }
            )
        }
    }
}
