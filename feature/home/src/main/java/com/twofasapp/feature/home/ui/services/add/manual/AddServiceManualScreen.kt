package com.twofasapp.feature.home.ui.services.add.manual

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.twofasapp.designsystem.TwIcons
import com.twofasapp.designsystem.TwTheme
import com.twofasapp.designsystem.common.TwButton
import com.twofasapp.designsystem.common.TwIconButton
import com.twofasapp.designsystem.common.TwOutlinedTextField
import com.twofasapp.designsystem.common.TwTextButton
import com.twofasapp.designsystem.common.TwTopAppBar
import com.twofasapp.locale.TwLocale
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddServiceManualScreen(
    viewModel: AddServiceManualViewModel = koinViewModel(),
) {
    Column(
        modifier = Modifier
            .background(TwTheme.color.surface)
            .offset(y = (-16).dp)
            .imePadding()
    ) {
        TwTopAppBar(titleText = TwLocale.strings.addTitle, containerColor = Color.Transparent)
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
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            color = TwTheme.color.divider
        )

        TwButton(
            text = TwLocale.strings.addManualDoneCta, onClick = { }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
        )
        TwTextButton(
            text = TwLocale.strings.addManualHelpCta, onClick = { }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
        )

    }
}