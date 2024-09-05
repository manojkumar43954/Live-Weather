package com.example.weatherapp.domain.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

/**
 * [DataStoreManager] class to store and retrieve user preferences
 */
class DataStoreManager(context: Context) {

   private val Context.dataStore : DataStore<Preferences> by
   preferencesDataStore(name="WEATHER_APP")
   private val dataStore = context.dataStore

    companion object{
        val cityNameKey = stringPreferencesKey("CITY_NAME")
    }

    /**
     * Stores name entered by the user in the preference store
     * @param name user entered name
     */
    suspend fun storeName(name: String){
        dataStore.edit { pref ->
            pref[cityNameKey] = name
        }
    }

    /**
     * Retrieves the user's last searched country, state, or city name from the preferences store
     * If not found, an empty string is returned.
     */
    fun getLastSearchedName() : Flow<String>{
        return dataStore.data
            .catch { exception ->
                if(exception is IOException){
                  emit(emptyPreferences())
                }
                else {
                    throw exception
                }
            }
            .map { pref->
                pref[cityNameKey] ?: ""
            }
    }
}