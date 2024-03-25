package com.example.sacalacuenta.di

import android.content.Context
import com.example.sacalacuenta.data.prefs.CuentaPreferences
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