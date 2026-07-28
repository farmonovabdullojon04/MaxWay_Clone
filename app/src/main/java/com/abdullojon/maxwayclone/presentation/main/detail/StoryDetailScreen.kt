package com.abdullojon.maxwayclone.presentation.main.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class StoryDetailScreen(
    val imageUrl: String
): Screen {
    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ){
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
            IconButton(onClick = { AppAppNavigationDispatcher.back()}) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                    )
            }
        }
    }
}