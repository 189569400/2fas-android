package com.twofasapp.feature.home.ui.services.add.manual

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.twofasapp.data.services.domain.Service
import com.twofasapp.designsystem.TwIcons
import com.twofasapp.designsystem.TwTheme
import com.twofasapp.designsystem.common.TwButton
import com.twofasapp.designsystem.common.TwDivider
import com.twofasapp.designsystem.common.TwIconButton
import com.twofasapp.designsystem.common.TwOutlinedTextField
import com.twofasapp.designsystem.common.TwRadioButton
import com.twofasapp.designsystem.common.TwTextButton
import com.twofasapp.designsystem.common.TwTopAppBar
import com.twofasapp.designsystem.dialog.ListRadioDialog
import com.twofasapp.locale.TwLocale
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddServiceManualScreen(
    viewModel: AddServiceManualViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var advancedExpanded by remember { mutableStateOf(false) }
    var showAlgorithmDialog by remember { mutableStateOf(false) }
    var showRefreshTimeDialog by remember { mutableStateOf(false) }
    var showDigitsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(TwTheme.color.surface)
            .offset(y = (-16).dp)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .animateContentSize()
    ) {
        TwTopAppBar(
            titleText = TwLocale.strings.addTitle,
            containerColor = Color.Transparent
        )

        TwOutlinedTextField(
            value = uiState.serviceName,
            onValueChange = {},
            labelText = TwLocale.strings.addManualServiceName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
        )

        TwOutlinedTextField(
            value = uiState.serviceKey,
            onValueChange = {},
            labelText = TwLocale.strings.addManualServiceKey,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, end = 16.dp)
                .padding(top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = TwLocale.strings.addManualOther,
                color = TwTheme.color.onSurfacePrimary,
                style = TwTheme.typo.body1,
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = TwLocale.strings.addManualOtherOptional,
                color = TwTheme.color.onSurfaceSecondary,
                style = TwTheme.typo.body1,
            )
            Spacer(Modifier.weight(1f))
            TwIconButton(
                painter = if (advancedExpanded) {
                    TwIcons.ChevronUp
                } else {
                    TwIcons.ChevronDown
                },
                onClick = { advancedExpanded = advancedExpanded.not() },
            )
        }

        if (advancedExpanded) {
            TwOutlinedTextField(
                value = uiState.additionalInfo,
                onValueChange = {},
                labelText = TwLocale.strings.addManualAdditionalInfo,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = TwLocale.strings.addManualAdvanced,
                color = TwTheme.color.onSurfacePrimary,
                style = TwTheme.typo.body1,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = TwLocale.strings.addManualAdvancedDescription,
                color = TwTheme.color.onSurfaceSecondary,
                style = TwTheme.typo.body3,
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "TOTP",
                        color = TwTheme.color.onSurfacePrimary,
                        style = TwTheme.typo.body1,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )

                    TwRadioButton(
                        selected = uiState.authType == Service.AuthType.TOTP,
                        onClick = { viewModel.updateAuthType(Service.AuthType.TOTP) },
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "HOTP",
                        color = TwTheme.color.onSurfacePrimary,
                        style = TwTheme.typo.body1,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )

                    TwRadioButton(
                        selected = uiState.authType == Service.AuthType.HOTP,
                        onClick = { viewModel.updateAuthType(Service.AuthType.HOTP) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showAlgorithmDialog = true }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = TwLocale.strings.addManualAlgorithm,
                    color = TwTheme.color.onSurfacePrimary,
                    style = TwTheme.typo.body1,
                )

                Text(
                    text = uiState.algorithm.name,
                    color = TwTheme.color.onSurfaceSecondary,
                    style = TwTheme.typo.body3,
                )
            }

            TwDivider()

            when (uiState.authType) {
                Service.AuthType.TOTP -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showRefreshTimeDialog = true }
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = TwLocale.strings.addManualRefreshTime,
                            color = TwTheme.color.onSurfacePrimary,
                            style = TwTheme.typo.body1,
                        )

                        Text(
                            text = uiState.refreshTime.toString(),
                            color = TwTheme.color.onSurfaceSecondary,
                            style = TwTheme.typo.body3,
                        )
                    }
                }

                Service.AuthType.HOTP -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { }
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = TwLocale.strings.addManualInitialCounter,
                            color = TwTheme.color.onSurfacePrimary,
                            style = TwTheme.typo.body1,
                        )

                        Text(
                            text = uiState.hotpCounter.toString(),
                            color = TwTheme.color.onSurfaceSecondary,
                            style = TwTheme.typo.body3,
                        )
                    }
                }
            }

            TwDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDigitsDialog = true }
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = TwLocale.strings.addManualDigits,
                    color = TwTheme.color.onSurfacePrimary,
                    style = TwTheme.typo.body1,
                )

                Text(
                    text = uiState.digits.toString(),
                    color = TwTheme.color.onSurfaceSecondary,
                    style = TwTheme.typo.body3,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

        } else {
            TwDivider()
        }

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

        if (showAlgorithmDialog) {
            ListRadioDialog(
                onDismissRequest = { showAlgorithmDialog = false },
                options = Service.Algorithm.values().map { it.name },
                selectedIndex = Service.Algorithm.values().indexOf(uiState.algorithm),
                onOptionSelected = { index, _ -> viewModel.updateAlgorithm(Service.Algorithm.values()[index]) }
            )
        }

        if (showRefreshTimeDialog) {
            ListRadioDialog(
                onDismissRequest = { showRefreshTimeDialog = false },
                options = listOf("30", "60", "90"),
                selectedOption = uiState.refreshTime.toString(),
                onOptionSelected = { _, value -> viewModel.updateRefreshTime(value.toInt()) }
            )
        }

        if (showDigitsDialog) {
            ListRadioDialog(
                onDismissRequest = { showDigitsDialog = false },
                options = listOf("6", "7", "8"),
                selectedOption = uiState.digits.toString(),
                onOptionSelected = { _, value -> viewModel.updateDigits(value.toInt()) }
            )
        }
    }
}