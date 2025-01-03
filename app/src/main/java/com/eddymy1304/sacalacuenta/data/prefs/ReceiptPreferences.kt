package com.eddymy1304.sacalacuenta.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.eddymy1304.sacalacuenta.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReceiptPreferences(context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(name = context.getString(R.string.name_datastore_preferences))
    }

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