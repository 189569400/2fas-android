package com.twofasapp.feature.home.ui.services.add

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twofasapp.feature.home.ui.services.add.manual.AddServiceManualScreen
import com.twofasapp.feature.home.ui.services.add.scan.AddServiceScanScreen

@Composable
internal fun AddServiceModal(
    onAddManuallyClick: () -> Unit = {},
    onScanQrClick: () -> Unit = {},
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "main"
    ) {

        composable(route = "main") {
            AddServiceScanScreen(
                openManual = { navController.navigate("manual") },
                openGallery = { }
            )
        }

        composable(route = "manual") {
            AddServiceManualScreen()
        }
    }
}