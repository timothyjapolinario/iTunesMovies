package com.example.itunesmovies.presentation.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerMovieCardItem(
    imageHeight: Dp,
    padding: Dp = 16.dp
){


    val colors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.9f)
    )
    BoxWithConstraints() {


        val widthPx  = with(LocalDensity.current){(maxWidth - padding * 2f).toPx() }
        val heightPx = with(LocalDensity.current) { (imageHeight - padding).toPx() }
        val gradientWidthPx = 0.3f * heightPx
        val infiniteTransition = rememberInfiniteTransition()

        //What animation it should do.
        val shimmerAnimationSpec = infiniteRepeatable<Float>(
            animation = tween(
                durationMillis = 1300,
                delayMillis = 300,
                easing = LinearEasing
            )
        )

        // Values that will change
        val xPosition = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = widthPx,
            animationSpec = shimmerAnimationSpec
        )

        val yPosition = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = heightPx,
            animationSpec = shimmerAnimationSpec
        )

        //The drawing
        val brush = Brush.linearGradient(
            colors = colors,
            start = Offset(
                x = xPosition.value - gradientWidthPx,
                y = yPosition.value - gradientWidthPx
            ),
            end = Offset(
                x = xPosition.value,
                y = yPosition.value
            )
        )
        Column(modifier = Modifier.background(MaterialTheme.colors.background)){
            repeat(5){
                DrawShimmerCard(
                    imageHeight = imageHeight,
                    padding,
                    shimmerBrush = brush
                )
            }
        }
    }
}

@Composable
fun DrawShimmerCard(
    imageHeight: Dp,
    padding: Dp,
    shimmerBrush: Brush
){
    Column(modifier = Modifier.padding(padding)){
        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.padding(vertical = 8.dp)
        ){
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .background(shimmerBrush)
            )
        }
    }
}