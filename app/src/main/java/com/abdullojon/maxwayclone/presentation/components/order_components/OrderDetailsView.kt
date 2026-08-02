package com.abdullojon.maxwayclone.presentation.components.order_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.presentation.order.MyOrdersState
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderDetailsView(
    state: MyOrdersState,
    onBack: () -> Unit
) {
    val order = state.activeOrder
    
    val dateString = if (order != null) {
        val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        sdf.format(Date(order.createTime))
    } else "----"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable { onBack() }
            )
            Text(
                text = "Текущий заказ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth().height(140.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Box {
                AsyncImage(
                    model = order?.ls?.firstOrNull()?.productData?.image,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.burger_max),
                    placeholder = painterResource(id = R.drawable.burger_max),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black.copy(alpha = 0.3f)
                ) {}
                Column(modifier = Modifier.padding(12.dp).align(Alignment.BottomStart)) {
                    Text("Заказ №${order?.id ?: "----"}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Наличные", color = Color.White, fontSize = 13.sp)
                    Text(dateString, color = Color.White, fontSize = 13.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                PriceRow("Сумма товаров", "${order?.sum ?: 0} сум", isBold = true)
                
                order?.ls?.forEach { item ->
                    PriceRow("${item.productData.name} ${item.count}x", "${item.productData.cost * item.count} сум")
                }
                
                HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Color(0xFFEEEEEE))
                
                PriceRow("Сумма доставки", "0 сум")
                PriceRow("Адрес", order?.address ?: "---")
                
                HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Color(0xFFEEEEEE))
                
                PriceRow("Общая сумма", "${order?.sum ?: 0} сум", isBold = true, fontSize = 18)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Статус заказа №${order?.id ?: "----"}", fontWeight = FontWeight.Bold)
                Text(text = state.currentStatus.title, color = Color(0xFF51267D), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(12.dp))
                OrderStatusComponent(currentStatus = state.currentStatus)
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* pavtor zakas berish logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF51267D)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Повторить заказ", color = Color.White, fontWeight = FontWeight.Bold)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun PriceRow(
    label: String, 
    value: String, 
    isBold: Boolean = false, 
    fontSize: Int = 14,
    textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = fontSize.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = if (isBold) Color.Black else Color.Gray
        )
        Text(
            text = value,
            fontSize = fontSize.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}
