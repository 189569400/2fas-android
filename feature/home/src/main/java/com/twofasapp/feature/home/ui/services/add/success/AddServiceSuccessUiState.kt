package com.twofasapp.feature.home.ui.services.add.success

import com.twofasapp.data.services.domain.Service

internal data class AddServiceSuccessUiState(
    val service: Service? = null,
    val showNextCode: Boolean = false,
)

internal sealed interface AddServiceSuccessUiEvent {
    object AddedSuccessfully : AddServiceSuccessUiEvent
}