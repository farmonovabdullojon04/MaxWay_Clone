package com.abdullojon.maxwayclone.presentation.profile.service

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class PrivacyPolicyScreen : Screen {
    @Composable
    override fun Content() {
        PrivacyPolicyScreenContent()
    }
}

@Composable
fun PrivacyPolicyScreenContent() {
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
                text = "Политика конфиденциальности",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(end = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Мы делаем клиентов счастливыми",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "История компании MaxWay работает на быстрорастущем рынке Республики Узбекистан и ориентирована на удовлетворение растущего спроса рынка. Компания продемонстрировала отличные результаты за последние 4 лет и устойчиво растет за счет основного направления бизнеса: Продукты питания и напитки.",
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
