package com.abdullojon.maxwayclone.presentation.components.main_components

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdullojon.maxwayclone.R

data class BottomBarItem(
    val label: String,
    val iconRes: Int
)
@Composable
fun BottomBar(
    modifier: Modifier= Modifier
) {
    val context = LocalContext.current
    var selectedIndex by remember { mutableIntStateOf(0) }

    val items = listOf(
        BottomBarItem("Главное", R.drawable.ic_home),
        BottomBarItem("Мои заказы", R.drawable.ic_orders),
        BottomBarItem("Личное", R.drawable.ic_profile)
    )

    NavigationBar(modifier = modifier) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    Toast.makeText(context, "Bosildi: ${item.label}", Toast.LENGTH_SHORT).show()
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor =  Color(0xFF6C2BD9),
                    selectedTextColor = Color(0xFF6C2BD9),
                    unselectedIconColor = Color(0xFF9B9BA1),
                    unselectedTextColor = Color(0xFF9B9BA1),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}