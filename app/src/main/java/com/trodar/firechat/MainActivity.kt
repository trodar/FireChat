package com.trodar.firechat

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.trace
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil3.imageLoader
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp
import com.trodar.designtemplate.theme.FireChatTheme
import com.trodar.firechat.ui.FireChatApp
import com.trodar.firechat.ui.rememberFireChatState
import com.trodar.firechat.utils.isSystemInDarkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = resources.configuration.isSystemInDarkTheme,
                androidTheme = MainActivityUiState.Loading.shouldUseAndroidTheme,
                disableDynamicTheming = MainActivityUiState.Loading.shouldDisableDynamicTheming,
            ),
        )
        enableEdgeToEdge()
        setupOnCreate()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme(),
                    viewModel.uiState,
                ) { systemDark, uiState ->
                    ThemeSettings(
                        darkTheme = uiState.shouldUseDarkTheme(systemDark),
                        androidTheme = uiState.shouldUseAndroidTheme,
                        disableDynamicTheming = uiState.shouldDisableDynamicTheming,
                    )
                }
                    .onEach { themeSettings = it }
                    .map { it.darkTheme }
                    .distinctUntilChanged()
                    .collect { darkTheme ->
                        trace("FireChatEdgeToEdge") {
                            enableEdgeToEdge(
                                statusBarStyle = SystemBarStyle.auto(
                                    lightScrim = android.graphics.Color.TRANSPARENT,
                                    darkScrim = android.graphics.Color.TRANSPARENT,
                                ) { darkTheme },
                                navigationBarStyle = SystemBarStyle.auto(
                                    lightScrim = lightScrim,
                                    darkScrim = darkScrim,
                                ) { darkTheme },
                            )
                        }
                    }
            }
        }

        FirebaseApp.initializeApp(this)

        AppEventsLogger.activateApp(application)

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        checkPermission()

        this.imageLoader.diskCache?.clear()

        setContent {
            val appState = rememberFireChatState()

            FireChatTheme(
                darkTheme = themeSettings.darkTheme,
                androidTheme = themeSettings.androidTheme,
                disableDynamicTheming = themeSettings.disableDynamicTheming,
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier,
                ) {
                    FireChatApp(appState = appState)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.updateLastOnline()
        viewModel.updateDate()
    }

    private fun setupOnCreate() {
        window.setResizeSoftInputMode()
    }

    private fun checkPermission() {
        when (PackageManager.PERMISSION_GRANTED) {

            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA,
            ) -> {
            }

            else -> {
                permissionRequest.launch(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    )
                )
            }
        }
    }
}

fun Window.setResizeSoftInputMode() {
    @Suppress("DEPRECATION")
    setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
}


private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

data class ThemeSettings(
    val darkTheme: Boolean,
    val androidTheme: Boolean,
    val disableDynamicTheming: Boolean,
)
