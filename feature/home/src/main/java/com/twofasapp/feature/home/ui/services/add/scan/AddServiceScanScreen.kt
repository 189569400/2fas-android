package com.twofasapp.feature.home.ui.services.add.scan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.twofasapp.designsystem.TwIcons
import com.twofasapp.designsystem.TwTheme
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
    }
}