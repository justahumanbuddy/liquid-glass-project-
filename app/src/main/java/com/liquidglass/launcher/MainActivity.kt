package com.liquidglass.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.liquidglass.launcher.data.AppRepository
import com.liquidglass.launcher.data.LocalAppRepository
import com.liquidglass.launcher.ui.screens.LauncherRoot
import com.liquidglass.launcher.ui.theme.LiquidGlassTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // The launcher IS home — swallow back so we don't leave.
            }
        })

        setContent {
            val context = LocalContext.current
            val repo = remember { AppRepository(context.applicationContext) }
            CompositionLocalProvider(LocalAppRepository provides repo) {
                LiquidGlassTheme {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent)
                    ) {
                        LauncherRoot()
                    }
                }
            }
        }
    }
}
