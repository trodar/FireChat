package com.trodar.designtemplate.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.trodar.designtemplate.theme.color.backgroundDark
import com.trodar.designtemplate.theme.color.backgroundLightHighContrast
import com.trodar.designtemplate.theme.color.errorContainerDark
import com.trodar.designtemplate.theme.color.errorContainerLightHighContrast
import com.trodar.designtemplate.theme.color.errorDark
import com.trodar.designtemplate.theme.color.errorLightHighContrast
import com.trodar.designtemplate.theme.color.inverseOnSurfaceDark
import com.trodar.designtemplate.theme.color.inverseOnSurfaceLightHighContrast
import com.trodar.designtemplate.theme.color.inversePrimaryDark
import com.trodar.designtemplate.theme.color.inversePrimaryLightHighContrast
import com.trodar.designtemplate.theme.color.inverseSurfaceDark
import com.trodar.designtemplate.theme.color.inverseSurfaceLightHighContrast
import com.trodar.designtemplate.theme.color.onBackgroundDark
import com.trodar.designtemplate.theme.color.onBackgroundLightHighContrast
import com.trodar.designtemplate.theme.color.onErrorContainerDark
import com.trodar.designtemplate.theme.color.onErrorContainerLightHighContrast
import com.trodar.designtemplate.theme.color.onErrorDark
import com.trodar.designtemplate.theme.color.onErrorLightHighContrast
import com.trodar.designtemplate.theme.color.onPrimaryContainerDark
import com.trodar.designtemplate.theme.color.onPrimaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.onPrimaryDark
import com.trodar.designtemplate.theme.color.onPrimaryLightHighContrast
import com.trodar.designtemplate.theme.color.onSecondaryContainerDark
import com.trodar.designtemplate.theme.color.onSecondaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.onSecondaryDark
import com.trodar.designtemplate.theme.color.onSecondaryLightHighContrast
import com.trodar.designtemplate.theme.color.onSurfaceDark
import com.trodar.designtemplate.theme.color.onSurfaceLightHighContrast
import com.trodar.designtemplate.theme.color.onSurfaceVariantDark
import com.trodar.designtemplate.theme.color.onSurfaceVariantLightHighContrast
import com.trodar.designtemplate.theme.color.onTertiaryContainerDark
import com.trodar.designtemplate.theme.color.onTertiaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.onTertiaryDark
import com.trodar.designtemplate.theme.color.onTertiaryLightHighContrast
import com.trodar.designtemplate.theme.color.outlineDark
import com.trodar.designtemplate.theme.color.outlineLightHighContrast
import com.trodar.designtemplate.theme.color.outlineVariantDark
import com.trodar.designtemplate.theme.color.outlineVariantLightHighContrast
import com.trodar.designtemplate.theme.color.primaryContainerDark
import com.trodar.designtemplate.theme.color.primaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.primaryDark
import com.trodar.designtemplate.theme.color.primaryLightHighContrast
import com.trodar.designtemplate.theme.color.scrimDark
import com.trodar.designtemplate.theme.color.scrimLightHighContrast
import com.trodar.designtemplate.theme.color.secondaryContainerDark
import com.trodar.designtemplate.theme.color.secondaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.secondaryDark
import com.trodar.designtemplate.theme.color.secondaryLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceBrightDark
import com.trodar.designtemplate.theme.color.surfaceBrightLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceContainerDark
import com.trodar.designtemplate.theme.color.surfaceContainerHighDark
import com.trodar.designtemplate.theme.color.surfaceContainerHighLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceContainerHighestDark
import com.trodar.designtemplate.theme.color.surfaceContainerHighestLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceContainerLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceContainerLowDark
import com.trodar.designtemplate.theme.color.surfaceContainerLowLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceContainerLowestDark
import com.trodar.designtemplate.theme.color.surfaceContainerLowestLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceDark
import com.trodar.designtemplate.theme.color.surfaceDimDark
import com.trodar.designtemplate.theme.color.surfaceDimLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceLightHighContrast
import com.trodar.designtemplate.theme.color.surfaceVariantDark
import com.trodar.designtemplate.theme.color.surfaceVariantLightHighContrast
import com.trodar.designtemplate.theme.color.tertiaryContainerDark
import com.trodar.designtemplate.theme.color.tertiaryContainerLightHighContrast
import com.trodar.designtemplate.theme.color.tertiaryDark
import com.trodar.designtemplate.theme.color.tertiaryLightHighContrast

private val DarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val LightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

@Composable
fun FireChatTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    androidTheme: Boolean = false,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        androidTheme -> if (darkTheme) DarkColorScheme else LightColorScheme
        !disableDynamicTheming && supportsDynamicTheming() -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S