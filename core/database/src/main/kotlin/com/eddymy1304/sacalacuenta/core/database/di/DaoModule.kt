package com.eddymy1304.sacalacuenta.core.database.di

import com.eddymy1304.sacalacuenta.core.database.AppDataBase
import com.eddymy1304.sacalacuenta.core.database.dao.ReceiptDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {

    @Provides
    fun provideReceiptDao(
        db: AppDataBase
    ) : ReceiptDao {
        return db.receiptDao()
    }

}