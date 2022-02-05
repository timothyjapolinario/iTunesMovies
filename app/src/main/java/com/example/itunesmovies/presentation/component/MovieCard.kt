package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.itunesmovies.models.Movie

@Composable
fun MovieCard(
    movie:Movie,
    onClick: ()-> Unit,
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ){
        Row{
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color.Cyan)
            ){
                ShimmerRecipeCardItem(imageHeight = 100.dp)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color.Green)
            ){
                Text(movie.trackName)
            }
        }
    }
}
@Preview
@Composable
fun MovieCardPreview(){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .clickable(onClick = { }),
        elevation = 8.dp
    ){
        Row{
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color.Cyan)
            ){
                ShimmerRecipeCardItem(imageHeight = 100.dp)
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(color = Color.Green)
            ){

            }
        }
    }
}