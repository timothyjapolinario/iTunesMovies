package com.example.itunesmovies.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import coil.compose.ImagePainter


/**
 * Custom class for image that has a place holder in case of loading.
 * @param pointer
 * @param placeHolder
 * @param contentDescription
 * @param modifier
 * @param alignment
 * @param contentScale
 * @param alpha
 * @param colorFilter
 * */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CustomImage(
    painter: ImagePainter,
    placeHolder: @Composable () -> Unit,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    Box(modifier){
        AnimatedVisibility(
            visible = when(painter.state){
                is ImagePainter.State.Empty,
                is ImagePainter.State.Success,
                -> true
                is ImagePainter.State.Loading,
                is ImagePainter.State.Error,
                -> false
            },
            enter = fadeIn()
        )
        {
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = contentDescription,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
                modifier = modifier,
            )
        }
        AnimatedVisibility(
            visible = when(painter.state){
                is ImagePainter.State.Empty,
                is ImagePainter.State.Success,
                -> false
                is ImagePainter.State.Loading,
                is ImagePainter.State.Error,
                -> true
            }
        ) {
            placeHolder()
        }
    }
}