package com.eddymy1304.sacalacuenta.core.data.di

import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepository
import com.eddymy1304.sacalacuenta.core.data.repository.ReceiptRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun provideReceiptRepository(
        receiptRepository: ReceiptRepositoryImpl
    ): ReceiptRepository

}