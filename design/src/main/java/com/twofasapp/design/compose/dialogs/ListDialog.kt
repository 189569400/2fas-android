package com.twofasapp.design.compose.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twofasapp.design.compose.dialogs.internal.BaseDialog
import com.twofasapp.design.theme.radioColors
import com.twofasapp.designsystem.TwTheme

@Composable
fun ListDialog(
    title: String? = null,
    items: List<String> = emptyList(),
    selected: String? = null,
    onDismiss: () -> Unit = {},
    onSelected: (Int, String) -> Unit = { _, _ -> }
) {
    BaseDialog(
        title = title,
        applyContentModifier = false,
        onDismiss = onDismiss,
    ) {
        if (items.isNotEmpty()) {
            LazyColumn {
                items(items) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelected(items.indexOf(it), it)
                                onDismiss.invoke()
                            }
                            .padding(vertical = 12.dp, horizontal = 24.dp)
                    ) {

                        if (selected != null) {
                            RadioButton(
                                selected = selected == it,
                                colors = radioColors(),
                                onClick = {
                                    onSelected(items.indexOf(it), it)
                                    onDismiss.invoke()
                                },
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(start = 16.dp)
                                    .align(CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(32.dp))
                        }

                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium.copy(color = TwTheme.color.onSurfacePrimary),
                            modifier = Modifier
                                .align(CenterVertically)
                                .wrapContentWidth()
                        )
                    }
                }
            }
        }
    }
}