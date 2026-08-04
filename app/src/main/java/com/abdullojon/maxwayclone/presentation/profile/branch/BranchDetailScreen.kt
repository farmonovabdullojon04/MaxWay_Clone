package com.abdullojon.maxwayclone.presentation.profile.branch

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.data.source.remote.dto.response.branches.BranchData
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class BranchDetailScreen(private val branch: BranchData) : Screen {
    @Composable
    override fun Content() {
        BranchDetailScreenContent(branch)
    }
}

@Composable
fun BranchDetailScreenContent(
    branch: BranchData
) {
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
                text = "Филиалы",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f).padding(end = 24.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    AsyncImage(
                        model = "https://maxway.uz/_next/image?url=https%3A%2F%2Fcdn.delever.uz%2Fdelever%2F62444c11-9f7a-4934-8c88-e9f02909156b&w=1920&q=75",
                        contentDescription = null,
                        error = painterResource(id = R.drawable.branch),
                        placeholder = painterResource(id = R.drawable.branch),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Crop
                    )
                    
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = branch.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        DetailRow(
                            icon = Icons.Default.Send,
                            text = branch.address
                        )
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF7F7F7))
                        
                        DetailRow(
                            icon = Icons.Default.LocationOn,
                            text = "Mo'ljal: Markaz" 
                        )
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF7F7F7))
                        
                        DetailRow(
                            icon = Icons.Default.Call,
                            text = branch.phone
                        )
                        
                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF7F7F7))
                        
                        DetailRow(
                            icon = Icons.Default.Schedule,
                            text = "с ${branch.openTime} до ${branch.closeTime}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF3EEFB)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF51267D),
                modifier = Modifier.size(18.dp)
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(start = 12.dp),
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
