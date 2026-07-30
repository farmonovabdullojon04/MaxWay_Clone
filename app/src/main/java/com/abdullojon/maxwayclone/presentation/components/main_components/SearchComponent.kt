package com.abdullojon.maxwayclone.presentation.components.main_components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    placeholder: String = "Поиск",
    onQueryChange: (String) -> Unit = {},
    onCancel: () -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFFF0F0F3))
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Qidiruv",
                tint = Color(0xFF9B9BA1),
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(10.dp))

            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color(0xFF9B9BA1),
                        fontSize = 15.sp
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = {
                        query = it
                        onQueryChange(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        query = ""
                        onQueryChange("")
                    },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Color(0xFF9B9BA1),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        if (query.isNotEmpty() || isFocused) {
            TextButton(
                onClick = {
                    query = ""
                    onQueryChange("")
                    onCancel()
                },
                contentPadding = PaddingValues(start = 8.dp)
            ) {
                Text(
                    text = "Отменить",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    SearchBar(modifier = Modifier.padding(16.dp))
}