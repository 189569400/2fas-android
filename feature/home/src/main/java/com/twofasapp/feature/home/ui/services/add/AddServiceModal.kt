package com.twofasapp.feature.home.ui.services.add

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.twofasapp.feature.home.ui.services.add.manual.AddServiceManualScreen
import com.twofasapp.feature.home.ui.services.add.scan.AddServiceScanScreen
import com.twofasapp.feature.home.ui.services.add.success.AddServiceSuccessScreen

internal object NavArg {
    val ServiceId = navArgument("id") { type = NavType.LongType }
}

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
                onAddedSuccessfully = {
                    navController.navigate("success/$it") { popUpTo(0) { inclusive = true } }
                },
            )
        }

        composable(route = "manual") {
            AddServiceManualScreen()
        }

        composable(
            route = "success/{${NavArg.ServiceId.name}}",
            arguments = listOf(NavArg.ServiceId)
        ) {
            AddServiceSuccessScreen()
        }
    }
}