package com.eddymy1304.sacalacuenta.di

import android.content.Context
import androidx.room.Room
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.data.db.AppDataBase
import com.eddymy1304.sacalacuenta.data.db.Dao
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
    fun provideDao(db: AppDataBase): Dao {
        return db.receiptDao()
    }
}