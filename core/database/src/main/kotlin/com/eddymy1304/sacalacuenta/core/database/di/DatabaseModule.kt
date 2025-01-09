package com.eddymy1304.sacalacuenta.core.database.di

import android.content.Context
import androidx.room.Room
import com.eddymy1304.sacalacuenta.core.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) : AppDataBase {
        return  Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = "app_database_room"
        ).build()
    }
}