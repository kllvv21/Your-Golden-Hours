package com.example.yourgoldenhour.screens

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yourgoldenhour.R
import com.example.yourgoldenhour.components.AddInputField
import com.example.yourgoldenhour.components.AddLocationField
import com.example.yourgoldenhour.components.AddPhotoButton
import com.example.yourgoldenhour.components.BlobBackground
import com.example.yourgoldenhour.components.BottomMenu
import com.example.yourgoldenhour.components.SaveButton
import com.example.yourgoldenhour.components.ScreenHeader
import com.example.yourgoldenhour.ui.theme.YourGoldenHourTheme

@Composable
fun AddPhotoScreen(paddings:PaddingValues,
                   onBackClick: () -> Unit
                   ) {
    val horizontalPadding = Modifier.padding(horizontal = 25.dp)
    BlobBackground {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding =
                paddings,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ScreenHeader(
                    title = "Добавить новое место",
                    onBackClick = onBackClick,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            }
            item {
                AddPhotoButton(
                    modifier = Modifier
                        .padding(horizontal = 60.dp, vertical = 20.dp)
                )
            }
            item {
                AddInputField(
                    title = "Название",
                    placeholder = "Введите название",
                    onValueChange = {},
                    singleLine = true,
                    modifier = horizontalPadding
                )
            }
            item {
                AddInputField(
                    title = "Описание",
                    placeholder = "Введите описание фотографии",
                    onValueChange = {},
                    modifier = horizontalPadding
                )
            }
            item {
                AddLocationField(
                    modifier = horizontalPadding.padding(top = 10.dp)
                )
            }
            item {
                SaveButton(
                    modifier = horizontalPadding.padding(vertical = 20.dp)
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewAddPhotoScreen() {
    YourGoldenHourTheme {
        AddPhotoScreen(paddings = PaddingValues(0.dp),
            onBackClick = {})
    }
}