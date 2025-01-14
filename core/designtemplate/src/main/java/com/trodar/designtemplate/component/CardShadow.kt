package com.trodar.designtemplate.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.trodar.designtemplate.theme.color.cardColors
import com.trodar.designtemplate.theme.color.shadowColor

@Composable
fun CardShadow(
    @DrawableRes id: Int,
    onClick: () -> Unit,
){
    val cornerShape = 30
    Card(
        modifier = Modifier.size(width = 72.dp, height = 56.dp)
            .shadow(
                elevation = 6.dp,
                ambientColor = shadowColor,
                spotColor = shadowColor,
                shape = AbsoluteRoundedCornerShape(
                    topLeftPercent = cornerShape,
                    topRightPercent = cornerShape,
                    bottomLeftPercent = cornerShape,
                    bottomRightPercent = cornerShape,
                )
            )
        ,
        onClick = onClick,
        colors = cardColors(),


        ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = id),
                contentDescription = "",
                modifier = Modifier.size(24.dp))
        }
    }
}