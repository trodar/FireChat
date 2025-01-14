package com.trodar.designtemplate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.color.shadowColor

@Composable
fun BoxShadow(
    height: Dp,
    width: Dp,
    radius: Dp,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier.customShadow(height, width, radius)
    ) {
        content()
    }
}

@Composable
fun Modifier.customShadow(
    height: Dp,
    width: Dp,
    radius: Dp,
    modifier: Modifier = Modifier,
) = this.then(
    modifier
        .padding(5.dp)
        .shadow(
            shadowColor,
            borderRadius = 1.dp,
            offsetX = 0.dp,
            offsetY = 15.dp,
            spread = (-6).dp,
            blurRadius = radius - 6.dp
        )
        .size(height = height, width = width)
        .clip(RoundedCornerShape(radius))
        .background(MaterialTheme.colorScheme.background)
)