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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.domain.model.ProductUIData
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class BasketScreen : Screen {
    @Composable
    override fun Content() {
        BasketScreenContent()
    }
}

@Composable
fun BasketScreenContent(
    viewModel: BasketViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val totalPrice = state.items.sumOf { it.cost * it.count }
    var showDeleteDialog by remember { mutableStateOf(false) }
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
                modifier = Modifier
                    .size(24.dp)
                    .clickable { 
                        if (state.items.isNotEmpty())
                            showDeleteDialog=true
                    }
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
                items(state.items, key = { it.id }) { item ->
                    BasketProductItem(
                        item = item,
                        onQuantityChange = { newQty ->
                            viewModel.updateCount(item.id, newQty)
                        }
                    )
                }
            }

            if (state.items.isNotEmpty()) {
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
                    .background(if (state.items.isNotEmpty()) Color(0xFF51267D) else Color.Gray)
                    .clickable(enabled = state.items.isNotEmpty()) { /* Buyurtma berish */ },
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
    if (showDeleteDialog) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = {
                Text(text = "Внимание!",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            },
            text = {
                Text(text = "Вы уверены, что хотите очистить корзину?",
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth())
            },
            confirmButton = {
                androidx.compose.material3.Button(
                    onClick = {
                        viewModel.clearBasket()
                        showDeleteDialog = false
                    },
                    modifier = Modifier.fillMaxWidth(0.45f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults
                        .buttonColors(containerColor = Color(0xFF51267D))
                ) {
                    Text("Да")
                }
            },
            dismissButton = {
                androidx.compose.material3.Button(
                    onClick = { showDeleteDialog = false },
                    modifier = Modifier.fillMaxWidth(0.45f).height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults
                        .buttonColors(containerColor = Color(0xFFF0F0F3))
                ) {
                    Text("Отменить", color = Color.Black)
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(20.dp)
        )
    }
}

@Composable
fun BasketProductItem(
    item: ProductUIData,
    onQuantityChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.image,
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
                    text = "${item.cost} сум",
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
                        .clickable { onQuantityChange(item.count - 1) }
                )
                Text(
                    text = item.count.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Plus",
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onQuantityChange(item.count + 1) }
                )
            }
        }
    }
}
