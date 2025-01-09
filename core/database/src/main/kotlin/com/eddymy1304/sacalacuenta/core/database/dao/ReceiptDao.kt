package com.eddymy1304.sacalacuenta.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.eddymy1304.sacalacuenta.core.database.model.DetailReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptEntity
import com.eddymy1304.sacalacuenta.core.database.model.ReceiptWithDetailReceipt
import kotlinx.coroutines.flow.Flow

@Dao
interface ReceiptDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipts(vararg receipts: ReceiptEntity)

    @Query("SELECT * FROM ReceiptEntity ORDER BY id DESC LIMIT 1")
    suspend fun getLastReceiptRegister(): ReceiptEntity

    @Update
    suspend fun updateReceipts(vararg receipts: ReceiptEntity)

    @Delete
    suspend fun deleteReceipts(vararg receipts: ReceiptEntity)

    @Transaction
    @Query("SELECT * FROM ReceiptEntity")
    fun getReceiptsWithDetail(): Flow<List<ReceiptWithDetailReceipt>>

    @Transaction
    @Query("SELECT * FROM ReceiptEntity c WHERE c.date = :date")
    fun getReceiptsByDate(date:String): Flow<List<ReceiptWithDetailReceipt>>

    @Transaction
    @Query("SELECT * FROM ReceiptEntity ORDER BY id DESC LIMIT 1")
    fun getLastReceiptWithDetail(): Flow<ReceiptWithDetailReceipt>

    @Transaction
    @Query("SELECT * FROM ReceiptEntity c WHERE c.id = :id")
    fun getReceiptWithDetById(id: Int) : Flow<ReceiptWithDetailReceipt>

    @Insert
    suspend fun insertDetailReceipt(listDetailReceipt: List<DetailReceiptEntity>)

    @Update
    suspend fun updateDetailReceipt(vararg detailReceipt: DetailReceiptEntity)

    @Delete
    suspend fun deleteDetailReceipt(vararg detailReceipt: DetailReceiptEntity)
}