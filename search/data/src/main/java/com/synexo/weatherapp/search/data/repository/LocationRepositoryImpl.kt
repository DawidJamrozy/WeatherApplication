package com.synexo.weatherapp.search.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.synexo.weatherapp.core.domain.model.Location
import com.synexo.weatherapp.search.domain.repository.LocationRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

internal class LocationRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocationRepository {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Flow<Location> {
        return callbackFlow {
            val locationRequest = LocationRequest.Builder(1000)
                .apply {
                    setDurationMillis(2000)
                    setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                }
                .build()

            val callBack = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location = locationResult.lastLocation!!
                    locationResult
                        .lastLocation
                        ?.let {
                            Location(
                                lat = location.latitude,
                                lng = location.longitude,
                            )
                        }
                        ?.let { trySend(it) }

                    client.removeLocationUpdates(this)
                }
            }

            client.requestLocationUpdates(locationRequest, callBack, Looper.getMainLooper())
            awaitClose { client.removeLocationUpdates(callBack) }
        }
    }
}