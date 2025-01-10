package com.eddymy1304.sacalacuenta.core.data.repository

import com.eddymy1304.sacalacuenta.core.database.model.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptWithDetailReceipt
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {

    suspend fun saveReceipts(vararg receipts: ReceiptEntity)

    suspend fun saveListDetailReceipt(list: List<DetailReceiptEntity>)

    suspend fun getLastReceiptRegister(): ReceiptEntity

    fun getLastReceiptWithDetail() : Flow<ReceiptWithDetailReceipt>

    fun getReceiptsWithDetail(): Flow<List<ReceiptWithDetailReceipt>>

    fun getReceiptsByDate(date: String): Flow<List<ReceiptWithDetailReceipt>>

    fun getReceiptWithDetById(id: Int): Flow<ReceiptWithDetailReceipt>

    fun getUserName() : Flow<String>

    suspend fun saveUserName(name: String)

}