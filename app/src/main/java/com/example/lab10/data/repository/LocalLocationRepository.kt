package com.example.lab10.data.repository

import com.example.lab10.data.local.dao.LocationDao
import com.example.lab10.data.local.entity.mapToEntity
import com.example.lab10.data.local.entity.mapToModel
import com.example.lab10.data.model.Location
import com.example.lab10.data.source.LocationDb
import com.example.lab10.domain.repository.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class LocalLocationRepository(
    private val locationDao: LocationDao
): LocationRepository {

    override suspend fun initialSync(): Boolean {
        return try {
            if (locationDao.getAllLocations().isEmpty()) {
                val locationDb = LocationDb()
                val locationsToInsert = locationDb.getAllLocations().map { it.mapToEntity() }
                locationDao.insertAll(locationsToInsert)
            }
            true
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            println(e)
            false
        }
    }

    override suspend fun getLocations(): List<Location> {
        delay(4000)
        return locationDao.getAllLocations().map { it.mapToModel() }
    }

    override suspend fun getLocationById(id: Int): Location {
        delay(2000)
        return locationDao.getLocationById(id).mapToModel()
    }
}