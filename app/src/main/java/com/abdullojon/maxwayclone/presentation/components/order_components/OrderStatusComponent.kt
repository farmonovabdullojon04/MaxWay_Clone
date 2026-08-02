package com.abdullojon.maxwayclone.presentation.components.order_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.TwoWheeler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.abdullojon.maxwayclone.domain.model.OrderStatus

@Composable
fun OrderStatusComponent(
    currentStatus: OrderStatus,
    modifier: Modifier = Modifier
) {
    val steps = OrderStatus.values()
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        steps.forEachIndexed { index, status ->
            val isActive = index <= currentStatus.step
            val isCompleted = index < currentStatus.step
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) Color(0xFF51267D) else Color(
                            0xFFF0F0F3
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when(index) {
                        0 -> Icons.AutoMirrored.Filled.ListAlt
                        1 -> Icons.Default.Storefront
                        2 -> Icons.Default.TwoWheeler
                        else -> Icons.Default.Check
                    },
                    contentDescription = null,
                    tint = if (isActive) Color.White else Color.Gray,
                    modifier = Modifier.size(18.dp)
                )
            }
            if (index < steps.size - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            if (index < currentStatus.step) Color(
                                0xFF51267D
                            ) else Color(
                                0xFFF0F0F3
                            )
                        )
                )
            }
        }
    }
}