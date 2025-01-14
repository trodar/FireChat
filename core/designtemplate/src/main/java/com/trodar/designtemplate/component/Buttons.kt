package com.trodar.designtemplate.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CornerShapeButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val cornerShape = 60
    BoxShadow(height =40.dp, width = 100.dp, radius = 32.dp) {

        Button(
            modifier = modifier.fillMaxSize(),
            enabled = enabled,
            shape = AbsoluteRoundedCornerShape(
                topLeftPercent = cornerShape,
                topRightPercent = cornerShape,
                bottomLeftPercent = cornerShape,
                bottomRightPercent = cornerShape,
            ),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            onClick = onClick
        ) {
            Text(text = text)
        }
    }
}