package com.abdullojon.maxwayclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdullojon.maxwayclone.R

@Composable
fun ProductCard(
    imageRes: Int,
    minusIconRes: Int,
    plusIconRes: Int,
    name: String,
    price: Int,
    modifier: Modifier= Modifier,
    onQuantityChanged: (Int)-> Unit ={}
){
    var quantity by remember { mutableIntStateOf(0) }
    Column(
        modifier=modifier
            .width(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(1.dp,Color(0xFFE0E0E0),RoundedCornerShape(16.dp))
            .padding(10.dp)
    ) {
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .height(110.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF5F5F7)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()

            )
        }
        Spacer(modifier= Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Spacer(modifier= Modifier.height(8.dp))
        if (quantity==0){
            Box(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF0F0F3))
                    .clickable{
                        quantity=1
                        onQuantityChanged(quantity)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$price сум",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }else{
            Row(
                modifier= Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF0F0F3))
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier= Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable{
                            quantity=(quantity-1).coerceAtLeast(0)
                            onQuantityChanged(quantity)
                     },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = minusIconRes),
                        contentDescription ="Kamaytirish",
                        modifier= Modifier.size(16.dp)
                    )
                }
                Text(
                    text = quantity.toString(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable {
                            quantity += 1
                            onQuantityChanged(quantity)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = plusIconRes),
                        contentDescription = "Ko'paytirish",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview(){
    ProductCard(
        imageRes = R.drawable.burger_max,
        minusIconRes = R.drawable.ic_minus,
        plusIconRes = R.drawable.ic_plus,
        name = "Макс Бургер",
        price = 25000
    )
}
