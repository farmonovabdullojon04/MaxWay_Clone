package com.abdullojon.maxwayclone.presentation.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.abdullojon.maxwayclone.presentation.components.main_components.BottomBar
import com.abdullojon.maxwayclone.presentation.components.order_components.CurrentOrderCard
import com.abdullojon.maxwayclone.presentation.components.order_components.OrderDetailsView
import com.abdullojon.maxwayclone.presentation.components.order_components.OrderHistoryItem
import com.abdullojon.maxwayclone.presentation.components.order_components.TabItem

class MyOrdersScreen : Screen {
    @Composable
    override fun Content() {
        MyOrdersScreenContent()
    }
}
@Composable
fun MyOrdersScreenContent(
    viewModel: MyOrdersViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    var selectedTab by remember { mutableIntStateOf(1) }

    Scaffold(
        bottomBar = { BottomBar() },
        containerColor = Color(0xFFF7F7F7)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .systemBarsPadding()
        ) {
            if (state.showDetails) {
                OrderDetailsView(
                    state = state,
                    onBack = { viewModel.toggleOrderDetails() }
                )
            } else {
                Text(
                    text = "Мои заказы",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFEEEEEE))
                        .padding(4.dp)
                ) {
                    TabItem(
                        text = "История заказов",
                        isSelected = selectedTab == 0,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTab = 0 }
                    )
                    TabItem(
                        text = "Текущие заказы",
                        isSelected = selectedTab == 1,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTab = 1 }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (selectedTab == 1) {
                    if (state.currentOrders.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Активных заказов нет", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.currentOrders) { order ->
                                CurrentOrderCard(
                                    orderNumber = order.id.toString(),
                                    statusTitle = state.currentStatus.title,
                                    currentStatus = state.currentStatus,
                                    onClick = { viewModel.selectOrder(order) }
                                )
                            }
                        }
                    }
                } else {
                    if (state.historyOrders.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "История заказов пуста", color = Color.Gray)
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.historyOrders) { order ->
                                OrderHistoryItem(
                                    order = order,
                                    onClick = { viewModel.selectOrder(order) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
