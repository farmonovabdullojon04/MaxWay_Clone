package com.abdullojon.maxwayclone.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Category(
    val id: String,
    val name: String
)

@Composable
fun CategoryBar(
    categories: List<Category>,
    selectedCategoryId: String?,
    modifier: Modifier = Modifier,
    onCategorySelected: (Category) -> Unit = {}
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        items(categories, key = { it.id }) { category ->
            val isSelected = category.id == selectedCategoryId
            Text(
                text = category.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (isSelected) Color.White else Color.Black,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .then(
                        if (isSelected) {
                            Modifier.background(Color(0xFF6C2BD9))
                        } else {
                            Modifier.border(1.dp, Color(0xFFE0E0E0), RoundedCornerShape(12.dp))
                        }
                    )
                    .clickable {
                        onCategorySelected(category)
                    }
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBarPreview() {
    val sampleCategories = listOf(
        Category("1", "Бургеры"),
        Category("2", "Сендвич"),
        Category("3", "Лаваш"),
        Category("4", "Картошка")
    )
    CategoryBar(
        categories = sampleCategories,
        selectedCategoryId = "1"
    )
}
