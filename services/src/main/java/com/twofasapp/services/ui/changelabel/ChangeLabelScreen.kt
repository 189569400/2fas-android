package com.twofasapp.services.ui.changelabel

import TextFieldOutlined
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twofasapp.designsystem.TwTheme
import com.twofasapp.designsystem.common.TwTopAppBar
import com.twofasapp.designsystem.ktx.LocalBackDispatcher
import com.twofasapp.prefs.model.Tint
import com.twofasapp.resources.R
import com.twofasapp.services.ui.ServiceViewModel
import com.twofasapp.services.view.ServiceIcon
import com.twofasapp.services.view.toColor

@Composable
internal fun ChangeLabelScreen(
    viewModel: ServiceViewModel,
) {
    val service = viewModel.uiState.collectAsState().value.service
    val labelText = remember { mutableStateOf(service.labelText.orEmpty()) }
    val labelTint = remember { mutableStateOf(service.labelBackgroundColor) }
    val backDispatcher = LocalBackDispatcher

    Scaffold(
        topBar = {
            TwTopAppBar(titleText = stringResource(id = R.string.customization_edit_label), actions = {
                TextButton(
                    onClick = {
                        viewModel.updateLabel(labelText.value.uppercase(), labelTint.value ?: Tint.LightBlue)
                        backDispatcher.onBackPressed()
                    },
                    enabled = labelText.value.isNotBlank() && labelText.value.isNotEmpty()
                ) {
                    Text(text = stringResource(id = R.string.commons__save))
                }
            })
        }
    ) { padding ->

        Column(Modifier.padding(padding)) {
            Box(modifier = Modifier.padding(24.dp)) {
                ServiceIcon(
                    service = service.copy(
                        labelText = labelText.value,
                        labelBackgroundColor = labelTint.value,
                    ),
                    modifier = Modifier.size(64.dp),
                    fontSize = 24.sp
                )
            }

            Box(modifier = Modifier.padding(24.dp)) {
                TextFieldOutlined(
                    value = labelText.value,
                    label = { Text(text = stringResource(id = R.string.tokens__label_characters_title)) },
                    maxChars = 2,
                    onValueChange = { labelText.value = it.uppercase() },
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Characters),
                )
            }

            LazyRow {
                items(Tint.values().toList(), key = { it.name }) {
                    Column(modifier = Modifier
                        .width(80.dp)
                        .clip(CircleShape)
                        .clickable { labelTint.value = it }
                        .padding(vertical = 12.dp)) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .align(Alignment.CenterHorizontally)
                                .clip(CircleShape)
                                .border(
                                    BorderStroke(if (it == labelTint.value) 50.dp else 5.dp, SolidColor(it.toColor())), CircleShape
                                )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            stringResource(
                                id = when (it) {
                                    Tint.Default -> com.twofasapp.resources.R.string.color__neutral
                                    Tint.LightBlue -> com.twofasapp.resources.R.string.color__light_blue
                                    Tint.Indigo -> com.twofasapp.resources.R.string.color__indigo
                                    Tint.Purple -> com.twofasapp.resources.R.string.color__purple
                                    Tint.Turquoise -> com.twofasapp.resources.R.string.color__turquoise
                                    Tint.Green -> com.twofasapp.resources.R.string.color__green
                                    Tint.Red -> com.twofasapp.resources.R.string.color__red
                                    Tint.Orange -> com.twofasapp.resources.R.string.color__orange
                                    Tint.Yellow -> com.twofasapp.resources.R.string.color__yellow
                                }
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(color = TwTheme.color.onSurfacePrimary),
                            modifier = Modifier
                                .padding(horizontal = 8.dp)
                                .align(Alignment.CenterHorizontally)
                                .wrapContentWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
