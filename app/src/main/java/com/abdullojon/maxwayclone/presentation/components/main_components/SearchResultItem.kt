package com.abdullojon.maxwayclone.presentation.components.main_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.domain.model.ProductUIData

@Composable
fun SearchResultItem(
    product: ProductUIData,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF7F7F7))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = product.image,
            contentDescription = product.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = product.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = product.description,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 2
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "${product.cost} сум",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6C2BD9)
        )
    }
}
