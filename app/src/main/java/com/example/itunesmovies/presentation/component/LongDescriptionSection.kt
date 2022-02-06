package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itunesmovies.models.Movie


@Composable
fun LongDescriptionSection(
    movieInfo: Movie
){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(start=7.dp, end = 7.dp)
    ){
        LongDescriptionPart(title = "Genre", content = movieInfo.primaryGenreName!!)
        Spacer(modifier = Modifier.height(5.dp))
        LongDescriptionPart(title = "Description", content = movieInfo.longDescription!!)
    }
}

@Composable
fun LongDescriptionPart(
    title: String,
    content: String,
){
    Column{
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = content,
            fontSize = 18.sp
        )
    }
}