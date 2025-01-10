package com.eddymy1304.sacalacuenta.core.data.repository

import com.eddymy1304.sacalacuenta.core.database.dao.ReceiptDao
import com.eddymy1304.sacalacuenta.core.database.model.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptWithDetailReceipt
import com.eddymy1304.sacalacuenta.datastore.ReceiptPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ReceiptRepositoryImpl @Inject constructor(
    private val receiptDao: ReceiptDao,
    private val receiptPreferences: ReceiptPreferences
) : ReceiptRepository {
    override suspend fun saveReceipts(vararg receipts: ReceiptEntity) {
        receiptDao.insertReceipts(*receipts)
    }

    override suspend fun saveListDetailReceipt(list: List<DetailReceiptEntity>) {
        receiptDao.insertDetailReceipt(list)
    }

    override suspend fun getLastReceiptRegister(): ReceiptEntity {
        return receiptDao.getLastReceiptRegister()
    }

    override fun getLastReceiptWithDetail(): Flow<ReceiptWithDetailReceipt> {
        return receiptDao.getLastReceiptWithDetail()
    }

    override fun getReceiptsWithDetail(): Flow<List<ReceiptWithDetailReceipt>> {
        return receiptDao.getReceiptsWithDetail()
    }

    override fun getReceiptsByDate(date: String): Flow<List<ReceiptWithDetailReceipt>> {
        return receiptDao.getReceiptsByDate(date)
    }

    override fun getReceiptWithDetById(id: Int): Flow<ReceiptWithDetailReceipt> {
        return receiptDao.getReceiptWithDetById(id)
    }

    override fun getUserName(): Flow<String> {
        return receiptPreferences.getUserName()
    }

    override suspend fun saveUserName(name: String) {
        receiptPreferences.saveUserName(name)
    }
}