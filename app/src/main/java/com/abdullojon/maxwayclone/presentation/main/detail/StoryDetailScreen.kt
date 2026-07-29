package com.abdullojon.maxwayclone.presentation.main.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories
import com.abdullojon.maxwayclone.navigation.AppAppNavigationDispatcher

class StoryDetailScreen(
    private val stories: List<Stories>,
    private val initialIndex: Int
): Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val pagerState= rememberPagerState(initialPage = initialIndex){
            stories.size
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ){
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {page->
                AsyncImage(
                    model = stories[page].url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
            IconButton(
                onClick = { AppAppNavigationDispatcher.back()},
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.White
                    )
            }
        }
    }
}