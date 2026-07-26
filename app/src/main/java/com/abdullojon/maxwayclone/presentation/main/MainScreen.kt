package com.abdullojon.maxwayclone.presentation.main


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.presentation.components.ProductCard
import com.abdullojon.maxwayclone.presentation.components.SearchBar

data class BurgerItem(
    val id: String,
    val name: String,
    val price: Int,
    val imageRes: Int
)

class MainScreen : Screen {
    @Composable
    override fun Content() {
        MainScreenContent()
    }
}

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel= hiltViewModel()
) {
    val products = remember {
        listOf(
            BurgerItem("1", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("2", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("3", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("4", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("5", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("6", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("7", "Макс Бургер", 25000, R.drawable.burger_max),
            BurgerItem("8", "Макс Бургер", 25000, R.drawable.burger_max)
        )
    }
    val state by viewModel.container.stateFlow.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var cart by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    val filteredProducts = if (searchQuery.isBlank()) {
        products
    } else {
        products.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }
    Column(modifier = modifier
        .fillMaxSize()
        .systemBarsPadding()) {
        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            onQueryChange = { searchQuery = it }
        )

        CategoryBar(
            categories = state.categories.map { Category(it.id.toString(), it.name) },
            selectedCategoryId = state.selectedCategoryId,
            onCategorySelected = { category ->
                viewModel.selectCategory(category.id)
            }
        )
        Spacer(modifier= Modifier.height(16.dp))
        Text(
            text = "Бургеры",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredProducts.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowProducts.forEach { product ->
                        ProductCard(
                            imageRes = product.imageRes,
                            minusIconRes = R.drawable.ic_minus,
                            plusIconRes = R.drawable.ic_plus,
                            name = product.name,
                            price = product.price,
                            modifier = Modifier.weight(1f),
                            onQuantityChanged = { qty ->
                                cart = cart.toMutableMap().apply {
                                    if (qty <= 0) remove(product.id) else put(product.id, qty)
                                }
                            }
                        )
                    }
                    if (rowProducts.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
        BottomBar()
    }
}

@Composable
fun CategoryChip(name: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF0F0F3))
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent()
}