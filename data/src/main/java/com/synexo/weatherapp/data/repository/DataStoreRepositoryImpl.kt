package com.synexo.weatherapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.synexo.weatherapp.domain.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * DataStoreRepository interface implementation.
 * Manages application data storage using DataStore.
 *
 * @constructor Instantiates DataStoreRepositoryImpl.
 * @param context Context for data store creation.
 */
internal class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreRepository {

    /**
     * Constants for DataStoreRepositoryImpl.
     */
    private companion object {
        /**
         * Data store identifier.
         */
        const val DATASTORE_NAME = "SETTINGS"
    }

    /**
     * Data store access property.
     * Utilizes context to access data store.
     */
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = DATASTORE_NAME)

    /**
     * Persists a value in the data store.
     *
     * @param key Identifier for the value to be stored.
     * @param value Data to be stored.
     */
    override suspend fun <T> saveToDataStore(key: Preferences.Key<T>, value: T) {
        context
            .dataStore
            .edit { it[key] = value }
    }

    /**
     * Retrieves a value from the data store.
     * Returns default value if key is not found.
     *
     * @param key Identifier for the value to be retrieved.
     * @param value Default value when key is not found.
     * @return Value associated with key or default value if key is not present.
     */
    override fun <T> readFromDataStore(key: Preferences.Key<T>, value: T?): Flow<T?> =
        context
            .dataStore
            .data
            .map { it[key] ?: value }

}