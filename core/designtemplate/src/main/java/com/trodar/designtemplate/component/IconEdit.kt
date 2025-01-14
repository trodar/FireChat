package com.trodar.designtemplate.component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.FireChatTheme

@Composable
fun IconEdit(
    icon: ImageVector,
    height: Dp,
    width: Dp,
    radius: Dp,
    text: String,
    label: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        val color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray

        BoxShadow(height = height, width = width, radius = radius) {
            BasicTextField(
                value = text,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = color),
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(start = 44.dp)
                    .fillMaxWidth()
                    .height(height),
                visualTransformation = if (keyboardType != KeyboardType.Password)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                cursorBrush = SolidColor(color),
                decorationBox = {
                    Box(
                        modifier = Modifier.padding(start = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        if (text.isBlank()) {
                            Text(
                                text = label,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                        }
                        it()
                    }
                }

            )
        }
        BoxShadow(
            height = 48.dp,
            width = 48.dp,
            radius = 24.dp,
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "icon",
                    tint = Color(0xFF03A9F4)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatIconPreview() {
    FireChatTheme {
        val text = "Hello Word"
        IconEdit(
            icon = Icons.Rounded.AccessTime,
            height = 32.dp,
            width = 440.dp,
            radius = 32.dp,
            text,
            keyboardType = KeyboardType.Password,
            label = "write here",
        ) {}
    }
}