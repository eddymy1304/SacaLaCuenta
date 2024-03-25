package com.example.sacalacuenta.di

import android.content.Context
import androidx.room.Room
import com.example.sacalacuenta.R
import com.example.sacalacuenta.data.db.AppDataBase
import com.example.sacalacuenta.data.db.Daos
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = context.getString(R.string.name_database_room)
        ).build()
    }

    @Provides
    @Singleton
    fun provideDaos(db: AppDataBase): Daos {
        return db.cuentaDao()
    }
}