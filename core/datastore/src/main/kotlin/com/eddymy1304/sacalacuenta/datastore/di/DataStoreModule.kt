package com.eddymy1304.sacalacuenta.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    internal fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(name = "data_store_preferences")
        }
        return dataStore
    }
}