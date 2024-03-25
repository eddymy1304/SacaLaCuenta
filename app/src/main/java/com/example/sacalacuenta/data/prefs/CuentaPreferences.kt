package com.example.sacalacuenta.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.sacalacuenta.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CuentaPreferences(context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile(name = context.getString(R.string.name_datastore_preferences))
    }

    companion object {
        val NAME_USER_KEY = stringPreferencesKey("name_user_key")
    }

    fun getNameUser(): Flow<String> {
        return dataStore.data.map { prefs -> prefs[NAME_USER_KEY].orEmpty() }
    }
}