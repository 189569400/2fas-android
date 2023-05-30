package com.twofasapp.feature.home.ui.services.add.scan

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.twofasapp.common.ktx.launchScoped
import com.twofasapp.common.ktx.runSafely
import com.twofasapp.data.services.ServicesRepository
import com.twofasapp.feature.qrscan.ReadQrFromImage
import com.twofasapp.parsers.OtpLinkParser
import com.twofasapp.parsers.domain.OtpAuthLink
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber

internal class AddServiceScanViewModel(
    private val context: Context,
    private val servicesRepository: ServicesRepository,
) : ViewModel() {

    private val qrReader = ReadQrFromImage(context)

    val uiState: MutableStateFlow<AddServiceScanUiState> = MutableStateFlow(AddServiceScanUiState())
    val uiEvents: MutableSharedFlow<AddServiceScanUiEvent> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    fun onScanned(text: String) {
        Timber.d("Scanned: $text")

        uiState.update {
            it.copy(
                scanned = text,
                enabled = false,
            )
        }

        insertService(text)
    }

    private fun insertService(text: String) {
        launchScoped {
            val link = OtpLinkParser.parseOtpAuthLink(text)

            if (servicesRepository.isSecretValid(link.secret).not()) {
                uiState.update { it.copy(showInvalidQrDialog = true) }
                return@launchScoped
            }

            if (servicesRepository.isServiceExists(link.secret)) {
                uiState.update { it.copy(showServiceExistsDialog = true) }
                return@launchScoped
            }

            saveService(text)
        }
    }

    fun saveService(text: String) {
        saveService(OtpLinkParser.parseOtpAuthLink(text))
    }

    private fun saveService(link: OtpAuthLink) {
        launchScoped {
            runSafely { servicesRepository.addService(link) }
                .onSuccess { uiEvents.emit(AddServiceScanUiEvent.AddedSuccessfully(it)) }
                .onFailure { uiState.update { it.copy(showErrorDialog = true) } }
        }
    }

    fun resetScanner() {
        uiState.update {
            it.copy(
                showInvalidQrDialog = false,
                showServiceExistsDialog = false,
                showErrorDialog = false,
                showGalleryErrorDialog = false,
            )
        }

        launchScoped {
            delay(500)
            uiState.update { it.copy(enabled = true) }
        }
    }

    fun loadImage(uri: Uri) {
        launchScoped {
            qrReader.invoke(uri)
                .onSuccess { text ->
                    uiState.update { it.copy(scanned = text) }
                    insertService(text)
                }
                .onFailure { uiState.update { it.copy(showGalleryErrorDialog = true) } }
        }
    }
}
