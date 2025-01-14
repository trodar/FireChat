package com.trodar.designtemplate.theme.color

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val shadowColor = Color(0xFF03A9F4)

@Composable
fun cardColors() = CardDefaults.cardColors(
    contentColor = MaterialTheme.colorScheme.onBackground,
    containerColor = MaterialTheme.colorScheme.background,
)


