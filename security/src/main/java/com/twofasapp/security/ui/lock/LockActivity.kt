package com.twofasapp.security.ui.lock

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.twofasapp.base.AuthTracker
import com.twofasapp.data.session.SettingsRepository
import com.twofasapp.design.theme.ThemeState
import com.twofasapp.designsystem.MainAppTheme
import com.twofasapp.extensions.makeWindowSecure
import com.twofasapp.resources.R
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LockActivity : AppCompatActivity() {

    private val authTracker: AuthTracker by inject()
    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeState.applyTheme(settingsRepository.getAppSettings().selectedTheme)

        overridePendingTransition(0, 0)
        if (resources.getBoolean(R.bool.isPortraitOnly)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            settingsRepository.observeAppSettings().collect {
                makeWindowSecure(allow = it.allowScreenshots)
            }
        }

        setContent {
            MainAppTheme {
                LockScreen {
                    authTracker.onAuthenticated()
                    finishWithSuccess()
                }
            }
        }
    }

    private fun finishWithSuccess() {
        authTracker.onAuthenticated()
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onResume() {
        super.onResume()
        val canGoBack = intent.getBooleanExtra("canGoBack", false)

        if (canGoBack.not()) {
            authTracker.onAuthenticateScreen()
        }
    }

    override fun onBackPressed() {
        val canGoBack = intent.getBooleanExtra("canGoBack", false)

        if (canGoBack) {
            finish()
        } else {
            setResult(Activity.RESULT_CANCELED)
            finishAffinity()
        }
    }
}