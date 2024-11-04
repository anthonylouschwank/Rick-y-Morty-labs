package com.example.lab10.domain.repository

import com.example.lab10.data.model.Location

interface LocationRepository {
    suspend fun initialSync(): Boolean
    suspend fun getLocations(): List<Location>
    suspend fun getLocationById(id: Int): Location
}