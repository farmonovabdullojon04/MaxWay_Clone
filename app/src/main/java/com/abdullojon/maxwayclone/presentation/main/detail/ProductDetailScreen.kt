package com.abdullojon.maxwayclone.presentation.main.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.main.main.MainViewModel

class ProductDetailScreen(
    private val productId: Int,
    private val categoryTitle: String
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: MainViewModel = hiltViewModel()
        val state by viewModel.container.stateFlow.collectAsState()
        val product = state.allProducts.find { it.id == productId }
        if (product != null) {
            ProductDetailContent(
                categoryTitle = categoryTitle,
                productName = product.name,
                description = product.description,
                price = product.cost,
                imageUrl = product.image,
                count = product.count,
                onBackClick = { AppAppNavigationDispatcher.back() },
                onCountChange = { newCount ->
                    viewModel.updateProductCount(productId, newCount)
                }
            )
        }
    }
}

@Composable
fun ProductDetailContent(
    categoryTitle: String,
    productName: String,
    description: String,
    price: Int,
    imageUrl: String,
    count: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onCountChange: (Int) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Orqaga",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
                    .size(20.dp)
                    .clickable { onBackClick() }
            )
            Text(
                text = categoryTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(Color(0xFFF3EEFB)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = productName,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.85f)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = productName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color(0xFF7A7A80),
                lineHeight = 20.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (count == 0) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF51267D))
                        .clickable { onCountChange(1) }
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Добавить ($price сум)", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            } else {
                Row(
                    modifier = Modifier
                        .height(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Kamaytirish",
                        modifier = Modifier
                            .size(44.dp)
                            .clickable { onCountChange((count - 1).coerceAtLeast(0)) }
                    )
                    Text(
                        text = count.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Ko'paytirish",
                        modifier = Modifier
                            .size(44.dp)
                            .clickable { onCountChange(count + 1) }
                    )
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF51267D))
                        .padding(horizontal = 18.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Добавить", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "${price * count} сум", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
