package com.eddymy1304.sacalacuenta.feature.receipt

import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.core.common.UiText
import com.eddymy1304.sacalacuenta.core.domain.GetReceipts
import com.eddymy1304.sacalacuenta.core.domain.SaveReceipt
import com.eddymy1304.sacalacuenta.core.model.DetailReceipt
import com.eddymy1304.sacalacuenta.core.model.Receipt
import com.eddymy1304.sacalacuenta.core.viewmodel.BaseViewModel
import com.eddymy1304.sacalacuenta.feature.receipt.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val saveReceipt: SaveReceipt,
    private val getReceipts: GetReceipts
) : BaseViewModel() {

    private val _receipt = MutableStateFlow(Receipt())
    val receipt: StateFlow<Receipt> = _receipt.asStateFlow()

    private val _listDetailReceipt = MutableStateFlow(listOf(DetailReceipt()))
    val listDetailReceipt: StateFlow<List<DetailReceipt>> = _listDetailReceipt.asStateFlow()

    private val _total = MutableStateFlow(0.00)
    val total: StateFlow<Double> = _total.asStateFlow()

    private val _idReceiptToNavigate = MutableStateFlow(-1)
    val idReceiptToNavigate: StateFlow<Int> = _idReceiptToNavigate.asStateFlow()

    private val _isOpenMenuPaymentMethod = MutableStateFlow(false)
    val isOpenMenuPaymentMethod: StateFlow<Boolean> = _isOpenMenuPaymentMethod.asStateFlow()

    fun saveReceiptWithListDet(receipt: Receipt, listDet: List<DetailReceipt>) {

        if (!validateReceipt(receipt, listDet)) return

        viewModelScope.launch {
            startLoading()
            saveReceipt(receipt, listDet)
            stopLoading()
            resetReceipt()
            navToScreenTicketByLastReceipt()
        }
    }

    private fun validateReceipt(
        receipt: Receipt,
        listDet: List<DetailReceipt>
    ): Boolean {
        return when {
            receipt.title.isBlank() -> {
                setMessage(UiText.StringResource(R.string.empty_field_title))
                false
            }

            receipt.paymentMethod.isBlank() -> {
                setMessage(UiText.StringResource(R.string.empty_field_payment_method))
                false
            }

            listDet.isEmpty() -> {
                setMessage(UiText.StringResource(R.string.empty_list_detail_receipt))
                false
            }

            listDet.any { it.total == 0.00 } -> {
                setMessage(UiText.StringResource(R.string.empty_field_total_detail_receipt))
                false
            }

            else -> true
        }
    }

    private fun resetReceipt() {
        _receipt.update { Receipt() }
        _listDetailReceipt.update { listOf(DetailReceipt()) }
        _total.update { 0.00 }
    }

    private fun navToScreenTicketByLastReceipt() {
        viewModelScope.launch {
            startLoading()
            getReceipts.getLastReceiptWithDetail().collect { receiptWithDetail ->
                _idReceiptToNavigate.update { receiptWithDetail.receipt.id }
                stopLoading()
            }
        }
    }

    fun updateTotalList() {
        _total.value = _listDetailReceipt.value.sumOf { it.total }
    }

    fun resetNavTo() {
        _idReceiptToNavigate.update { -1 }
    }

    fun deleteDetailReceipt(position: Int) {
        val listFiltered = _listDetailReceipt.value.filterIndexed { index, _ ->
            index != position
        }
        _listDetailReceipt.update { listFiltered }
        updateTotalList()
    }

    fun addDetailReceipt(det: DetailReceipt = DetailReceipt()) {
        _listDetailReceipt.update { _listDetailReceipt.value.plus(det) }
    }

    fun setIsOpenMenuPaymentMethod(isOpen: Boolean) {
        _isOpenMenuPaymentMethod.update { isOpen }
    }

    fun setOnPaymentMethodChanged(paymentMethod: String) {
        _receipt.update {
            _receipt.value.copy(paymentMethod = paymentMethod)
        }
    }

    fun setOnNameItemChanged(name: String, position: Int) {
        _listDetailReceipt.update {
            _listDetailReceipt.value.mapIndexed { i, det ->
                if (i == position && det.name.length <= 20) det.copy(name = name)
                else det
            }
        }
        updateTotalList()
    }

    fun setOnAmountItemChanged(textAmount: String, position: Int) {
        _listDetailReceipt.update {
            _listDetailReceipt.value.mapIndexed { i, det ->
                if (i == position) {

                    val filterAmount = Utils.filterNumberDecimal(textAmount, 4, 2)

                    val newAmount = try {
                        filterAmount.toDouble()
                    } catch (e: Exception) {
                        0.00
                    }

                    if (det.price > 0.00) {
                        val total = newAmount * det.price
                        det.copy(amount = newAmount, total = total)
                    } else {
                        det.copy(amount = newAmount)
                    }
                } else det
            }
        }
        updateTotalList()
    }

    fun setOnPriceItemChanged(textPrice: String, position: Int) {
        _listDetailReceipt.update {
            _listDetailReceipt.value.mapIndexed { i, det ->
                if (i == position) {

                    val filterPrice = Utils.filterNumberDecimal(textPrice, 4, 2)

                    val newPrice = try {
                        filterPrice.toDouble()
                    } catch (e: Exception) {
                        0.00
                    }

                    if (det.amount > 0.00) {
                        val total = det.amount * newPrice
                        det.copy(price = newPrice, total = total)
                    } else {
                        det.copy(price = newPrice)
                    }

                } else det
            }
        }
        updateTotalList()
    }

    fun setOnTotalItemChanged(textTotal: String, position: Int) {
        _listDetailReceipt.update {
            _listDetailReceipt.value.mapIndexed { i, det ->
                if (i == position) {
                    val filterTotal = Utils.filterNumberDecimal(textTotal, 4, 2)
                    val newTotal = try {
                        filterTotal.toDouble()
                    } catch (e: Exception) {
                        0.00
                    }
                    det.copy(total = newTotal)
                } else det
            }
        }
        updateTotalList()
    }

    fun setOnLockedItemChanged(position: Int) {
        _listDetailReceipt.update {
            _listDetailReceipt.value.mapIndexed { i, det ->
                if (i == position) det.copy(
                    price = 0.00,
                    total = 0.00,
                    amount = 0.00,
                    itemLocked = !det.itemLocked
                )
                else det
            }
        }
        updateTotalList()
    }

    fun onValueChangeTitle(text: String) {
        if (text.length <= 20)
            _receipt.update {
                _receipt.value.copy(title = text)
            }
    }
}