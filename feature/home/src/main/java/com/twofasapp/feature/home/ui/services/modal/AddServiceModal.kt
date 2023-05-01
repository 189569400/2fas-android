package com.twofasapp.feature.home.ui.services.modal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twofasapp.designsystem.TwIcons
import com.twofasapp.designsystem.TwTheme
import com.twofasapp.designsystem.common.TwButton
import com.twofasapp.designsystem.common.TwIconButton
import com.twofasapp.designsystem.common.TwOutlinedTextField
import com.twofasapp.designsystem.common.TwTextButton
import com.twofasapp.designsystem.common.TwTopAppBar
import com.twofasapp.designsystem.settings.SettingsLink
import com.twofasapp.feature.qrscan.QrScan
import com.twofasapp.locale.TwLocale

@Composable
internal fun AddServiceModal(
    onAddManuallyClick: () -> Unit = {},
    onScanQrClick: () -> Unit = {},
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable(route = "main") {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                QrScan(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .fillMaxWidth()
                        .height(260.dp)
                )

                Text(
                    text = TwLocale.strings.addOtherMethods,
                    color = TwTheme.color.onSurfaceTertiary,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
                )

                SettingsLink(
                    title = TwLocale.strings.addEnterManual,
                    icon = TwIcons.Keyboard
                ) { navController.navigate("manual") }

                SettingsLink(
                    title = TwLocale.strings.addFromGallery,
                    icon = TwIcons.Panorama
                ) { onScanQrClick() }
            }
        }

        composable(route = "manual") {

            Column(
                modifier = Modifier.background(TwTheme.color.surface)
            ) {
                TwTopAppBar(titleText = "Pair service with 2FAS", containerColor = Color.Transparent)
                TwOutlinedTextField(
                    value = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    onValueChange = {},
                    labelText = TwLocale.strings.addManualServiceName
                )

                TwOutlinedTextField(
                    value = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp),
                    onValueChange = {},
                    labelText = TwLocale.strings.addManualServiceKey
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = TwLocale.strings.addManualOther, color = TwTheme.color.onSurfacePrimary)
                    Text(text = TwLocale.strings.addManualOtherOptional, color = TwTheme.color.onSurfaceSecondary)
                    Spacer(Modifier.weight(1f))
                    TwIconButton(
                        painter = if (true) {
                            TwIcons.ChevronUp
                        } else {
                            TwIcons.ChevronDown
                        },
                        onClick = { },
                    )
                }

                Divider(Modifier.fillMaxWidth().padding(vertical = 16.dp), color = TwTheme.color.divider)

                TwButton(
                    text = TwLocale.strings.addManualDoneCta, onClick = { }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                )
//                TwTextButton(
//                    text = TwLocale.strings.addManualHelpCta, onClick = { }, modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp, vertical = 4.dp)
//                )

            }
        }
    }

}