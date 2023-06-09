package com.twofasapp.di

import com.twofasapp.common.di.CommonModule
import com.twofasapp.data.browserext.di.DataBrowserExtModule
import com.twofasapp.data.notifications.di.DataNotificationsModule
import com.twofasapp.data.services.di.DataServicesModule
import com.twofasapp.data.session.di.DataSessionModule
import com.twofasapp.feature.about.di.AboutModule
import com.twofasapp.feature.appsettings.di.AppSettingsModule
import com.twofasapp.feature.externalimport.di.ExternalImportModule
import com.twofasapp.feature.home.di.HomeModule
import com.twofasapp.feature.startup.di.StartupModule
import com.twofasapp.feature.trash.di.TrashModule
import com.twofasapp.network.di.NetworkModule
import com.twofasapp.storage.di.StorageModule
import org.koin.core.module.Module

object Modules {
    private val app = listOf(
        AppModule(),
        CommonModule(),
        NetworkModule(),
        StorageModule(),
    )

    private val data = listOf(
        DataNotificationsModule(),
        DataSessionModule(),
        DataServicesModule(),
        DataBrowserExtModule(),
    )

    private val feature = listOf(
        MainModule(),
        StartupModule(),
        HomeModule(),
        ExternalImportModule(),
        AppSettingsModule(),
        TrashModule(),
        AboutModule(),
    )

    fun provide(): List<Module> =
        buildList {
            addAll(app)
            addAll(data)
            addAll(feature)
        }.map { it.provide() }
}