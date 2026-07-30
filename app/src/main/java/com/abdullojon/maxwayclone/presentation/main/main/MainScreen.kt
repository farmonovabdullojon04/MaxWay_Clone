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
import com.abdullojon.maxwayclone.presentation.basket.BasketScreen
import com.abdullojon.maxwayclone.presentation.components.main_components.BannerComponent
import com.abdullojon.maxwayclone.presentation.components.main_components.BottomBar
import com.abdullojon.maxwayclone.presentation.components.main_components.CartSummaryBar
import com.abdullojon.maxwayclone.presentation.components.main_components.Category
import com.abdullojon.maxwayclone.presentation.components.main_components.CategoryBar
import com.abdullojon.maxwayclone.presentation.components.main_components.EmptySearchPlaceholder
import com.abdullojon.maxwayclone.presentation.components.main_components.ProductCard
import com.abdullojon.maxwayclone.presentation.components.main_components.SearchBar
import com.abdullojon.maxwayclone.presentation.components.main_components.SearchResultItem
import com.abdullojon.maxwayclone.presentation.components.main_components.StoriesComponent
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
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.container.stateFlow.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val currentCategoryName = state.categories.find {
        it.id.toString() == state.selectedCategoryId
    }?.name ?: ""

    val totalItems = state.allProducts.sumOf { it.count }
    val totalPrice = state.allProducts.sumOf { it.count * it.cost }

    Column(modifier = modifier
        .fillMaxSize()
        .systemBarsPadding()) {
        SearchBar(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            onQueryChange = { text ->
                viewModel.onSearchQueryChange(text)
            },
            onCancel = {
                viewModel.onSearchCancel()
            }
        )

        if (state.isSearching) {
            if (state.filteredProducts.isEmpty()) {
                EmptySearchPlaceholder()
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.filteredProducts) { product ->
                        SearchResultItem(
                            product = product,
                            onClick = {
                                AppAppNavigationDispatcher.navigateTo(
                                    ProductDetailScreen(product.id, currentCategoryName)
                                )
                            }
                        )
                    }
                }
            }
        } else {
            StoriesComponent(
                stories = state.stories,
                onItemClick = { selectedStory ->
                    val index = state.stories.indexOf(selectedStory)
                    AppAppNavigationDispatcher.navigateTo(
                        StoryDetailScreen(stories = state.stories, initialIndex = index)
                    )
                }
            )
            BannerComponent(
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = currentCategoryName,
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
                                count = product.count,
                                minusIconRes = R.drawable.ic_minus,
                                plusIconRes = R.drawable.ic_plus,
                                name = product.name,
                                price = product.cost,
                                modifier = Modifier.weight(1f),
                                onCountChange = { newCount ->
                                    viewModel.updateProductCount(product.id, newCount)
                                },
                                onClick = {
                                    AppAppNavigationDispatcher.navigateTo(
                                        ProductDetailScreen(product.id, currentCategoryName)
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
        }

        CartSummaryBar(
            totalItems = totalItems,
            totalPrice = totalPrice,
            onClick = { AppAppNavigationDispatcher.navigateTo(BasketScreen()) }
        )
        BottomBar()
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    MainScreenContent()
}