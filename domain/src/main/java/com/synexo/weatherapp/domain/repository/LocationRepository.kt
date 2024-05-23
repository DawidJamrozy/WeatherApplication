package com.synexo.weatherapp.domain.repository

import com.synexo.weatherapp.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getCurrentLocation(): Flow<Location>

}