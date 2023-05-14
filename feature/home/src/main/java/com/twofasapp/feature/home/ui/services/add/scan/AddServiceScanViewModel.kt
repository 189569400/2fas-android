package com.twofasapp.feature.home.ui.services.add.scan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

internal class AddServiceScanViewModel : ViewModel() {

    val uiState: MutableStateFlow<AddServiceScanUiState> = MutableStateFlow(AddServiceScanUiState())
}
