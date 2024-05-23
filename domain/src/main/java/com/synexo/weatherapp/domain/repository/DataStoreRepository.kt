package com.synexo.weatherapp.domain.repository

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

/**
 * Interface for managing application data storage.
 */
interface DataStoreRepository {

    /**
     * Persists a value in the data store, identified by a specific key.
     *
     * @param key Unique identifier for the value to be stored.
     * @param value Data to be stored.
     */
   suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T)

    /**
     * Retrieves a value from the data store using a specific key. Returns null if the key is not found.
     *
     * @param key Unique identifier for the value to be retrieved.
     * @param value Default value returned when the key is not found in the data store.
     * @return Value associated with the key in the data store, or null if the key is not present.
     */
     fun <T> readFromDataStore(key: Preferences.Key<T>, value: T? = null): Flow<T?>

}