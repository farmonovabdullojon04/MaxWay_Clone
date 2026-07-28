package com.abdullojon.maxwayclone.presentation.main.main


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher
import com.abdullojon.maxwayclone.presentation.components.BottomBar
import com.abdullojon.maxwayclone.presentation.components.Category
import com.abdullojon.maxwayclone.presentation.components.CategoryBar
import com.abdullojon.maxwayclone.presentation.components.ProductCard
import com.abdullojon.maxwayclone.presentation.components.RecommendationComponent
import com.abdullojon.maxwayclone.presentation.components.SearchBar
import com.abdullojon.maxwayclone.presentation.components.StoriesComponent
import com.abdullojon.maxwayclone.presentation.main.detail.ProductDetailScreen
import com.abdullojon.maxwayclone.presentation.main.detail.StoryDetailScreen

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
    val state by viewModel.container.stateFlow.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var cart by remember { mutableStateOf<Map<String, Int>>(emptyMap()) }
    val currentCategoryName=state.categories.find {
        it.id.toString()==state.selectedCategoryId
    }?.name?:""
    Column(modifier = modifier
        .fillMaxSize()
        .systemBarsPadding()) {
        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            onQueryChange = { searchQuery = it }
        )
        
        StoriesComponent(
            stories = state.stories,
            onItemClick = {story->
                AppAppNavigationDispatcher.navigateTo(StoryDetailScreen(story.url))
            }
        )

        RecommendationComponent(
            ads = state.ads,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        CategoryBar(
            categories = state.categories.map { Category(it.id.toString(), it.name) },
            selectedCategoryId = state.selectedCategoryId,
            onCategorySelected = { category ->
                viewModel.selectCategory(category.id)
            }
        )
        Spacer(modifier= Modifier.height(8.dp))
        Text(
            text =currentCategoryName,
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
            items(state.filteredProducts.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowProducts.forEach { product ->
                        ProductCard(
                            imageUrl = product.image,
                            minusIconRes = R.drawable.ic_minus,
                            plusIconRes = R.drawable.ic_plus,
                            name = product.name,
                            price = product.cost,
                            modifier = Modifier.weight(1f),
                            onQuantityChanged = { qty ->
                                cart = cart.toMutableMap().apply {
                                    val key = product.id.toString()
                                    if (qty <= 0) remove(key) else put(key, qty)
                                }
                            },
                            onClick = {
                                AppAppNavigationDispatcher.navigateTo(
                                    ProductDetailScreen(product, currentCategoryName)
                                )
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

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent()
}