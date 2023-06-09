package com.twofasapp.services.data

import com.twofasapp.common.time.TimeProvider
import com.twofasapp.prefs.model.RecentlyDeletedService
import com.twofasapp.prefs.model.ServiceDto
import com.twofasapp.prefs.model.ServicesOrder
import com.twofasapp.prefs.usecase.RecentlyDeletedPreference
import com.twofasapp.prefs.usecase.ServicesOrderPreference
import com.twofasapp.services.domain.model.Service
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

internal class ServicesRepositoryImpl(
    private val localData: ServicesLocalData,
    private val servicesOrderPreference: ServicesOrderPreference,
    private val recentlyDeletedPreference: RecentlyDeletedPreference,
    private val timeProvider: TimeProvider,
) : ServicesRepository {

    override fun select(): Single<List<ServiceDto>> {
        return localData.select()
    }

    override suspend fun insertService(service: Service): Long {
        val possibleDuplicate = localData.selectAll().find {
            it.secret.lowercase() == service.secret.lowercase()
        }

        possibleDuplicate?.let {
            localData.delete(it.id)
        }

        return localData.insertService(service)
    }

    override fun selectFlow(): Flow<List<Service>> {
        return localData.selectFlow()
    }

    override suspend fun select(serviceId: Long): Service {
        return localData.selectAll().first { it.id == serviceId }
    }

    override fun observe(serviceId: Long): Flow<Service> {
        return localData.observe(serviceId)
    }

    override fun observe(): Flowable<List<ServiceDto>> {
        return localData.observe()
    }

    override fun insertService(service: ServiceDto): Single<Long> {
        return localData.insertService(service)
    }

    override fun updateService(vararg services: ServiceDto): Completable {
        return localData.updateService(*services)
    }

    override fun deleteService(vararg services: ServiceDto): Completable {
        return localData.deleteService(*services)
    }

    override fun deleteService(id: Long): Completable {
        return localData.deleteService(id)
    }

    override suspend fun getServicesOrder(): ServicesOrder {
        return servicesOrderPreference.get()
    }

    override suspend fun updateServicesOrder(servicesOrder: ServicesOrder) {
        servicesOrderPreference.put(servicesOrder)
    }

    override suspend fun assignServiceDomain(service: Service, domain: String) {
        if (service.assignedDomains.contains(domain.lowercase())) {
            return
        }

        localData.updateServiceSuspend(
            service.copy(
                assignedDomains = service.assignedDomains.plus(domain.lowercase()),
                updatedAt = timeProvider.systemCurrentTime(),
            )
        )
    }

    override suspend fun updateService(vararg services: Service) {
        localData.updateServiceSuspend(*services)
    }

    override suspend fun addToRecentlyDeleted(secret: String) {
        recentlyDeletedPreference.put {
            it.copy(services = it.services.plus(RecentlyDeletedService(secret, deletedAt = timeProvider.systemCurrentTime())))
        }
    }
}