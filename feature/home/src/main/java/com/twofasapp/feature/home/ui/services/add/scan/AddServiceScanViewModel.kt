package com.twofasapp.feature.home.ui.services.add.scan

import androidx.lifecycle.ViewModel
import com.twofasapp.common.ktx.launchScoped
import com.twofasapp.data.services.ServicesRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

internal class AddServiceScanViewModel(
    private val servicesRepository: ServicesRepository,
) : ViewModel() {

    companion object {
        private const val GoogleAuthenticatorSchema = "otpauth-migration"
    }


    val uiState: MutableStateFlow<AddServiceScanUiState> = MutableStateFlow(AddServiceScanUiState())

    fun onScanned(text: String) {
        Timber.d("Scanned: $text")

        uiState.update {
            it.copy(
                scanned = text,
                enabled = false,
            )
        }

        when {
            text.lowercase().startsWith(GoogleAuthenticatorSchema) -> importGoogleAuthenticator(text)
            else -> importService(text)
        }
    }

    private fun importService(text: String) {
        launchScoped {

            if (servicesRepository.isSecretValid("").not()) {
                uiState.update { it.copy(showInvalidQrDialog = true) }
                return@launchScoped
            }

            if (servicesRepository.isServiceExists("")) {
                uiState.update { it.copy(showServiceExistsDialog = true) }
                return@launchScoped
            }


        }
    }

    private fun importGoogleAuthenticator(text: String) {

    }

    fun resetScanner() {
        uiState.update {
            it.copy(
                showInvalidQrDialog = false,
                showServiceExistsDialog = false,
            )
        }

        launchScoped {
            delay(500)
            uiState.update { it.copy(enabled = true) }
        }
    }
}
