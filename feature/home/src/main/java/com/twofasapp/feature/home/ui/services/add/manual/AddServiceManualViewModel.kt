package com.twofasapp.feature.home.ui.services.add.manual

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class AddServiceManualViewModel : ViewModel() {

    val uiState: MutableStateFlow<AddServiceManualUiState> = MutableStateFlow(AddServiceManualUiState())
}
