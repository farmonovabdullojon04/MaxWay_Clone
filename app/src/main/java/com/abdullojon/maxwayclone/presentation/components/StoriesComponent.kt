package com.abdullojon.maxwayclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.R
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Stories

data class Recommendation(
    val id: Int,
    val imageRes: Int,
    val name: String
)

@Composable
fun StoriesComponent(
    stories: List<Stories>,
    modifier: Modifier = Modifier,
    onItemClick: (Stories) -> Unit = {}
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(stories) { story ->
            Column(
                modifier = Modifier
                    .width(70.dp)
                    .clickable { onItemClick(story) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = story.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color(0xFFE0E0E0), CircleShape)
                )
            }
        }
    }
}
