package com.abdullojon.maxwayclone.presentation.profile.service

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class AboutServiceScreen : Screen {
    @Composable
    override fun Content() {
        AboutServiceScreenContent()
    }
}

@Composable
fun AboutServiceScreenContent() {
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
                text = "О сервисе",
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
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            ServiceMenuItem(text = "Политика конфиденциальности") { 
                AppAppNavigationDispatcher.navigateTo(PrivacyPolicyScreen())
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF7F7F7))
            ServiceMenuItem(text = "Лицензионные соглашения") {  }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFF7F7F7))
            ServiceMenuItem(text = "Работа в MaxWay") {  }
        }
    }
}

@Composable
fun ServiceMenuItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = Color(0xFF9B9BA1),
            modifier = Modifier.size(24.dp)
        )
    }
}
