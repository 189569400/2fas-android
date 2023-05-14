package com.twofasapp.feature.home.ui.services.add.manual

import androidx.lifecycle.ViewModel
import com.twofasapp.data.services.domain.Service
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class AddServiceManualViewModel : ViewModel() {

    val uiState: MutableStateFlow<AddServiceManualUiState> = MutableStateFlow(AddServiceManualUiState())

    fun updateAuthType(authType: Service.AuthType) {
        when (authType) {
            Service.AuthType.TOTP -> uiState.update {
                it.copy(
                    authType = authType,
                    hotpCounter = 0,
                )
            }

            Service.AuthType.HOTP -> uiState.update {
                it.copy(
                    authType = authType,
                    algorithm = Service.Algorithm.SHA1,
                )
            }
        }
    }

    fun updateAlgorithm(algorithm: Service.Algorithm) {
        uiState.update { it.copy(algorithm = algorithm) }
    }

    fun updateRefreshTime(value: Int) {
        uiState.update { it.copy(refreshTime = value) }
    }

    fun updateDigits(value: Int) {
        uiState.update { it.copy(digits = value) }
    }
}
