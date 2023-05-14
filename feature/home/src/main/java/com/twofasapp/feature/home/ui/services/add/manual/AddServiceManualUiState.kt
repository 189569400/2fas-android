package com.twofasapp.feature.home.ui.services.add.manual

import com.twofasapp.data.services.domain.Service

internal data class AddServiceManualUiState(
    val serviceName: String = "",
    val serviceKey: String = "",
    val additionalInfo: String = "",
    val authType: Service.AuthType = Service.AuthType.TOTP,
    val algorithm: Service.Algorithm = Service.Algorithm.SHA1,
    val hotpCounter: Int = 1,
    val refreshTime: Int = 30,
    val digits: Int = 6,
)
