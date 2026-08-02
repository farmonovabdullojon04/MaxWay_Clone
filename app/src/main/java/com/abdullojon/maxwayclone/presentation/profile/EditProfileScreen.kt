package com.abdullojon.maxwayclone.presentation.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.main.main.MainScreen
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditProfileScreen : Screen {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        EditProfileScreenContent()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreenContent(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val userData = state.userData

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }

    LaunchedEffect(userData) {
        userData?.let {
            name = it.name ?: ""
            surname = it.surname ?: ""
            phone = it.phone
            birthDate = it.birthDate ?: ""
        }
    }

    var showDatePicker by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7))
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { AppAppNavigationDispatcher.back() }
            )
            Text(
                text = "Редактировать профиль",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Logout,
                contentDescription = "Logout",
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { 
                        viewModel.logout()
                        AppAppNavigationDispatcher.navigateTo(MainScreen())
                    }
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            EditProfileField(label = "Имя", value = name, onValueChange = { name = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileField(label = "Фамилия", value = surname, onValueChange = { surname = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileField(label = "Номер телефона", value = phone, onValueChange = { phone = it })
            Spacer(modifier = Modifier.height(16.dp))
            EditProfileField(
                label = "Ваш дата рождения",
                value = birthDate,
                modifier = Modifier.clickable { showDatePicker = true },
                onValueChange = { },
                readOnly = true
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    val apiDate = try {
                        val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        LocalDate.parse(birthDate, inputFormatter).format(outputFormatter)
                    } catch (e: Exception) {
                        birthDate
                    }
                    
                    viewModel.updateUserInfo(
                        name = "$name $surname",
                        birthDate = apiDate,
                        onSuccess = { AppAppNavigationDispatcher.back() }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51267D)),
                shape = RoundedCornerShape(12.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        text = "Подтвердить",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Удалить аккаунт",
                color = Color.Red,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { 
                    viewModel.deleteAccount(
                        onSuccess = { AppAppNavigationDispatcher.navigateTo(MainScreen()) }
                    )
                }
            )
        }
    }

    if (showDatePicker) {
        ModalBottomSheet(
            onDismissRequest = { showDatePicker = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = null
        ) {
            DatePickerContent(
                initialDate = birthDate,
                onDateSelected = { 
                    birthDate = it
                    showDatePicker = false
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerContent(
    initialDate: String,
    onDateSelected: (String) -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    var selectedLocalDate by remember { 
        mutableStateOf(
            try { LocalDate.parse(initialDate, formatter) } catch (e: Exception) { LocalDate.now() }
        ) 
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Ваш дата рождения",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            WheelDatePicker(
                startDate = selectedLocalDate,
                minDate = LocalDate.of(1950, 1, 1),
                maxDate = LocalDate.now(),
                yearsRange = IntRange(1950, 2024),
                size = DpSize(300.dp, 180.dp),
                textStyle = MaterialTheme.typography.titleMedium,
                textColor = MaterialTheme.colorScheme.onSurface,
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    enabled = true,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                ),
                onSnappedDate = { snappedDate: LocalDate -> selectedLocalDate = snappedDate }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onDateSelected(selectedLocalDate.format(formatter)) },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51267D)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Подтвердить", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun EditProfileField(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            readOnly = readOnly,
            enabled = !readOnly,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            ),
            singleLine = true
        )
    }
}
