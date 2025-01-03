package com.eddymy1304.sacalacuenta.ui.screens

import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.R
import com.eddymy1304.sacalacuenta.base.BaseViewModel
import com.eddymy1304.sacalacuenta.data.models.DetailReceiptView
import com.eddymy1304.sacalacuenta.data.models.ReceiptView
import com.eddymy1304.sacalacuenta.data.models.Screen
import com.eddymy1304.sacalacuenta.data.models.Screen.*
import com.eddymy1304.sacalacuenta.domain.GetReceipts
import com.eddymy1304.sacalacuenta.domain.SaveReceipt
import com.eddymy1304.sacalacuenta.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val saveReceipt: SaveReceipt,
    private val getReceipts: GetReceipts
) : BaseViewModel() {

    private val _receipt = MutableStateFlow(ReceiptView())
    val receipt: StateFlow<ReceiptView> get() = _receipt

    private val _listDetailReceipt = MutableStateFlow(listOf(DetailReceiptView()))
    val listDetailReceipt: StateFlow<List<DetailReceiptView>> get() = _listDetailReceipt

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    private val _navTo: MutableStateFlow<Pair<Boolean, Screen>> = MutableStateFlow(Pair(false, ScreenReceipt))
    val navTo: StateFlow<Pair<Boolean, Screen>> get() = _navTo


    fun saveReceiptWithListDet(receipt: ReceiptView, listDet: List<DetailReceiptView>) {

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
        receipt: ReceiptView,
        listDet: List<DetailReceiptView>
    ): Boolean {
        return when {
            receipt.title.value.isNullOrBlank() -> {
                setMessage(UiText.StringResource(R.string.empty_field_title))
                false
            }

            receipt.paymentMethod.value.isNullOrBlank() -> {
                setMessage(UiText.StringResource(R.string.empty_field_payment_method))
                false
            }

            listDet.isEmpty() -> {
                setMessage(UiText.StringResource(R.string.empty_list_det_cuenta))
                false
            }

            listDet.any { it.total.value == null || it.total.value == 0.00 } -> {
                setMessage(UiText.StringResource(R.string.empty_field_total_det_cuenta))
                false
            }

            else -> true
        }
    }

    private fun resetReceipt() {
        _receipt.update { ReceiptView() }
        _listDetailReceipt.update { listOf(DetailReceiptView()) }
        _total.update { 0.00 }
    }

    private fun navToScreenTicketByLastReceipt() {
        viewModelScope.launch {
            startLoading()
            getReceipts.getLastReceiptWithDetail().collect { receiptWithDet ->
                _navTo.update {
                    Pair(true, ScreenTicket(id = receiptWithDet.receipt.id ?: throw Exception("id null")))
                }
                stopLoading()
            }
        }
    }

    fun updateTotal() {
        _total.value = _listDetailReceipt.value.sumOf { it.total.value ?: 0.00 }
    }

    fun resetNavTo() {
        _navTo.update { Pair(false, ScreenReceipt) }
    }

    fun deleteDetailReceipt(det: DetailReceiptView) {
        _listDetailReceipt.update { _listDetailReceipt.value.minus(det) }
    }

    fun addDetailReceipt(det: DetailReceiptView = DetailReceiptView()) {
        _listDetailReceipt.update { _listDetailReceipt.value.plus(det) }
    }
}