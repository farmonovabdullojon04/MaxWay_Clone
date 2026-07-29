package com.abdullojon.maxwayclone.presentation.basket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class BasketScreen : Screen {
    @Composable
    override fun Content() {
        BasketScreenContent()
    }
}

@Composable
fun BasketScreenContent() {
    val basketItems = remember {
        mutableStateListOf(
            BasketItem("1", "Клаб-сэндвич \"Янгилик\"", 19000, 1, ""),
            BasketItem("2", "Макс Бургер", 19000, 1, "")
        )
    }

    val totalPrice = basketItems.sumOf { it.price * it.quantity }

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
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { AppAppNavigationDispatcher.back() }
            )
            Text(
                text = "Оформить заказ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp)
            )
            Icon(
                painter = painterResource(id=R.drawable.delete),
                contentDescription = "Clear Basket",
                tint = Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { basketItems.clear() }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f, fill = false)
            ) {
                items(basketItems) { item ->
                    BasketProductItem(
                        item = item,
                        onQuantityChange = { newQty ->
                            val index = basketItems.indexOf(item)
                            if (newQty > 0) {
                                basketItems[index] = item.copy(quantity = newQty)
                            } else {
                                basketItems.removeAt(index)
                            }
                        }
                    )
                }
            }

            if (basketItems.isNotEmpty()) {
                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Сумма заказа",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Text(
                        text = "$totalPrice сум",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (basketItems.isNotEmpty()) Color(0xFF51267D) else Color.Gray)
                    .clickable(enabled = basketItems.isNotEmpty()) { /* Buyurtma berish uchun joy */ },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Оформить заказ",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun BasketProductItem(
    item: BasketItem,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.imageUrl,
            error = painterResource(id = R.drawable.burger_max),
            contentDescription = item.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "${item.price} сум",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6C2BD9),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_minus),
                    contentDescription = "Minus",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onQuantityChange(item.quantity - 1) }
                )
                Text(
                    text = item.quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Plus",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onQuantityChange(item.quantity + 1) }
                )
            }
        }
    }
}

data class BasketItem(
    val id: String,
    val name: String,
    val price: Int,
    val quantity: Int,
    val imageUrl: String
)

@Preview(showBackground = true)
@Composable
private fun BasketScreenPreview() {
    BasketScreenContent()
}
