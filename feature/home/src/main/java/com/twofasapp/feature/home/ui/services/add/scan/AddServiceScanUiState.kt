package com.twofasapp.feature.home.ui.services.add.scan

internal data class AddServiceScanUiState(
    val scanned: String = "",
    val enabled: Boolean = true,
    val showInvalidQrDialog: Boolean = false,
    val showServiceExistsDialog: Boolean = false,
)
