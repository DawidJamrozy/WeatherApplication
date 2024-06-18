package com.synexo.weatherapp.search.domain.repository

import com.synexo.weatherapp.core.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationRepository {

    suspend fun getCurrentLocation(): Flow<Location>

}