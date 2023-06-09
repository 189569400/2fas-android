package com.twofasapp.ui.main

import com.twofasapp.data.session.domain.SelectedTheme

internal data class MainUiState(
    val selectedTheme: SelectedTheme? = null,
    val startDestination: StartDestination? = null,
    val browserExtRequests: List<BrowserExtRequest> = emptyList(),
) {
    enum class StartDestination {
        Home, Onboarding
    }
}
