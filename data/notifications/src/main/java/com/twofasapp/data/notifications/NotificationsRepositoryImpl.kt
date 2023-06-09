package com.twofasapp.data.notifications

import com.twofasapp.common.coroutines.Dispatchers
import com.twofasapp.data.notifications.domain.Notification
import com.twofasapp.data.notifications.local.NotificationsLocalSource
import com.twofasapp.data.notifications.mappper.asDomain
import com.twofasapp.data.notifications.remote.NotificationsRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class NotificationsRepositoryImpl(
    private val dispatchers: Dispatchers,
    private val local: NotificationsLocalSource,
    private val remote: NotificationsRemoteSource,
) : NotificationsRepository {

    override suspend fun getNotifications(): List<Notification> {
        return withContext(dispatchers.io) {
            local.getNotifications().sortedByTime()
        }
    }

    override suspend fun fetchNotifications() {
        withContext(dispatchers.io) {
            val remoteData = remote.fetchNotifications()
            local.saveNotifications(remoteData.map { it.asDomain() })
        }
    }

    override suspend fun readAllNotifications() {
        withContext(dispatchers.io) {
            local.readAllNotifications()
        }
    }

    override fun hasUnreadNotifications(): Flow<Boolean> {
        return local.observeNotifications()
            .map { it.sortedByTime() }
            .map { list -> list.any { it.isRead.not() } }
    }

    private fun List<Notification>.sortedByTime(): List<Notification> {
        return sortedWith(compareBy({ it.isRead }, { it.publishTime.unaryMinus() }))
    }
}