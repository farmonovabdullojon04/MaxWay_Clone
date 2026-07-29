package com.abdullojon.maxwayclone.presentation.components.main_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abdullojon.maxwayclone.data.source.remote.dto.response.ads_stories.Ads

@Composable
fun BannerComponent(
    modifier: Modifier = Modifier,
    ads: List<Ads>
) {
    Column(modifier=modifier.fillMaxWidth()) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(ads){ad->
                AsyncImage(
                    model = ad.bannerUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(300.dp)
                        .height(140.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }
    }
}
