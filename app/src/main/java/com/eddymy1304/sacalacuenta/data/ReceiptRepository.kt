package com.eddymy1304.sacalacuenta.data

import com.eddymy1304.sacalacuenta.data.db.ReceiptEntity
import com.eddymy1304.sacalacuenta.data.db.Dao
import com.eddymy1304.sacalacuenta.data.db.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.data.prefs.ReceiptPreferences
import javax.inject.Inject

class ReceiptRepository @Inject constructor(
    private val receiptDao: Dao,
    private val preferences: ReceiptPreferences
) {
    suspend fun saveReceipts(vararg receipts: ReceiptEntity) = receiptDao.insertReceipts(*receipts)

    suspend fun getLastReceiptRegister() = receiptDao.getLastReceiptRegister()

    suspend fun saveListDetailReceipt(list: List<DetailReceiptEntity>) =
        receiptDao.insertDetailReceipt(list)

    fun getReceiptsWithDetail() = receiptDao.getReceiptsWithDetail()

    fun getLastReceiptWithDetail() = receiptDao.getLastReceiptWithDetail()

    fun getReceiptsByDate(date: String) = receiptDao.getReceiptsByDate(date)

    fun getReceiptWithDetById(id:Int) = receiptDao.getReceiptWithDetById(id)

    fun getUserName() = preferences.getUserName()

    suspend fun saveUserName(userName: String) = preferences.saveUserName(userName)
}