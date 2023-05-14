package com.twofasapp.feature.home.ui.services.add.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twofasapp.designsystem.TwIcons
import com.twofasapp.designsystem.TwTheme
import com.twofasapp.designsystem.dialog.ConfirmDialog
import com.twofasapp.designsystem.settings.SettingsLink
import com.twofasapp.feature.qrscan.QrScan
import com.twofasapp.locale.TwLocale
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddServiceScanScreen(
    viewModel: AddServiceScanViewModel = koinViewModel(),
    openManual: () -> Unit,
    openGallery: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        QrScan(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .height(260.dp),
            onScanned = {
                if (uiState.enabled) {
                    viewModel.onScanned(it)
                }
            }
        )

        Text(
            text = TwLocale.strings.addOtherMethods,
            color = TwTheme.color.onSurfaceTertiary,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        SettingsLink(
            title = TwLocale.strings.addEnterManual,
            icon = TwIcons.Keyboard
        ) { openManual() }

        SettingsLink(
            title = TwLocale.strings.addFromGallery,
            icon = TwIcons.Panorama
        ) { openGallery() }

        Spacer(modifier = Modifier.height(16.dp))
    }

    if (uiState.showInvalidQrDialog) {
        ConfirmDialog(
            onDismissRequest = { viewModel.resetScanner() },
            title = TwLocale.strings.addScanInvalidQrTitle,
            body = TwLocale.strings.addScanInvalidQrBody,
            positive = TwLocale.strings.addScanInvalidQrCta,
            negative = null,
        )
    }

    if (uiState.showServiceExistsDialog) {
        ConfirmDialog(
            onDismissRequest = { viewModel.resetScanner() },
            title = TwLocale.strings.addScanServiceExistsTitle,
            body = TwLocale.strings.addScanServiceExistsBody,
            positive = TwLocale.strings.addScanServiceExistsPositiveCta,
            negative = TwLocale.strings.addScanServiceExistsNegativeCta,
            onConfirm = { viewModel.resetScanner() },
            onDeny = { viewModel.resetScanner() },
        )
    }
}