package com.twofasapp.feature.home.ui.services.modal

internal sealed interface ModalType {
    object Empty : ModalType
    object AddService : ModalType
    data class FocusService(
        val id: Long,
        val isRecentlyAdded: Boolean,
    ) : ModalType
}
