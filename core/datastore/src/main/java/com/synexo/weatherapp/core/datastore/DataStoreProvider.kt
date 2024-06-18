package com.synexo.weatherapp.core.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStoreProvider {

    /**
     * Persists a value in the data store.
     *
     * @param key Identifier for the value to be stored.
     * @param value Data to be stored.
     */
    suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T)

    /**
     * Retrieves a value from the data store.
     * Returns default value if key is not found.
     *
     * @param key Identifier for the value to be retrieved.
     * @param value Default value when key is not found.
     * @return Value associated with key or default value if key is not present.
     */
    fun <T> readFromDataStore(key: Preferences.Key<T>, value: T?): Flow<T?>

}