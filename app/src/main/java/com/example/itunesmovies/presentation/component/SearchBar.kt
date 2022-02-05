package com.example.itunesmovies.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    modifier: Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
){
    var text by rememberSaveable{
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(true)
    }
    Box(){
        BasicTextField(
            value = text,
            onValueChange = {newString->
                text = newString
                onSearch(newString)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged { focusState ->
                    //Is the textfield clicked
                    isHintDisplayed = !focusState.isFocused
                }
        )
        if(isHintDisplayed && text.isEmpty()){
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}