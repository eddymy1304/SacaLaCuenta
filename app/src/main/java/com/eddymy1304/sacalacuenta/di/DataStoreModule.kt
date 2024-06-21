package com.eddymy1304.sacalacuenta.di

import android.content.Context
import com.eddymy1304.sacalacuenta.data.prefs.CuentaPreferences
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
    fun provideCuentaPreferences(@ApplicationContext context: Context): CuentaPreferences {
        return CuentaPreferences(context)
    }
}