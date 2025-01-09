package com.eddymy1304.sacalacuenta.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReceiptPreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val USER_NAME_KEY = stringPreferencesKey("user_name_key")
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.map { prefs -> prefs[USER_NAME_KEY].orEmpty() }
    }

    suspend fun saveUserName(userName: String) {
        dataStore.edit { prefs -> prefs[USER_NAME_KEY] = userName }
    }
}