package com.abdullojon.maxwayclone.presentation.components.main_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdullojon.maxwayclone.R

@Composable
fun EmptySearchPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.search_person),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}
