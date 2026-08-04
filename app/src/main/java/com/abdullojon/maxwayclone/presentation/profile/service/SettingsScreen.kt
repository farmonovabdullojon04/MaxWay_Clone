package com.abdullojon.maxwayclone.presentation.profile.service

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        SettingsScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent() {
    var personalMessagesEnabled by remember { mutableStateOf(true) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var showLanguageSheet by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Русский") }

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
                text = "Настройки",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(end = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsItemCard(onClick = { showLanguageSheet = true }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsIcon(Icons.Outlined.Language)
                    Text(
                        text = "Язык",
                        modifier = Modifier.weight(1f).padding(start = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = selectedLanguage,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.LightGray,
                        modifier = Modifier.size(20.dp).padding(start = 4.dp)
                    )
                }
            }
            SettingsItemCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsIcon(Icons.Outlined.MailOutline)
                    Text(
                        text = "Личные сообщения",
                        modifier = Modifier.weight(1f).padding(start = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Switch(
                        checked = personalMessagesEnabled,
                        onCheckedChange = { personalMessagesEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF51267D),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.LightGray,
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
            SettingsItemCard {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SettingsIcon(Icons.Outlined.NotificationsNone)
                    Text(
                        text = "Уведомлений",
                        modifier = Modifier.weight(1f).padding(start = 16.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Switch(
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = Color(0xFF51267D),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.LightGray,
                            uncheckedBorderColor = Color.Transparent
                        )
                    )
                }
            }
        }
    }

    if (showLanguageSheet) {
        ModalBottomSheet(
            onDismissRequest = { showLanguageSheet = false },
            sheetState = sheetState,
            containerColor = Color.White,
            dragHandle = null
        ) {
            LanguageSelectionContent(
                selectedLanguage = selectedLanguage,
                onLanguageSelected = { 
                    selectedLanguage = it
                    showLanguageSheet = false
                }
            )
        }
    }
}

@Composable
fun LanguageSelectionContent(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Язык",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LanguageOption(
            name = "Русский",
            iconRes = R.drawable.rus,
            isSelected = selectedLanguage == "Русский",
            onClick = { onLanguageSelected("Русский") }
        )
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF7F7F7))
        
        LanguageOption(
            name = "O'zbekcha",
            iconRes = R.drawable.uzb,
            isSelected = selectedLanguage == "O'zbekcha",
            onClick = { onLanguageSelected("O'zbekcha") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun LanguageOption(
    name: String,
    iconRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Text(
            text = name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            fontSize = 16.sp,
            color = Color.Black
        )
        
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color(0xFF51267D),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SettingsItemCard(onClick: () -> Unit = {}, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        content()
    }
}

@Composable
fun SettingsIcon(icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF3EEFB)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF51267D),
            modifier = Modifier.size(20.dp)
        )
    }
}
