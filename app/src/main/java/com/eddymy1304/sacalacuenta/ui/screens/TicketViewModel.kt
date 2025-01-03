package com.eddymy1304.sacalacuenta.ui.screens

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.base.BaseViewModel
import com.eddymy1304.sacalacuenta.data.models.ReceiptWithDetailView
import com.eddymy1304.sacalacuenta.domain.GetReceipts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val getReceipts: GetReceipts
) : BaseViewModel() {

    private val _receiptWithDet = MutableStateFlow(ReceiptWithDetailView())
    val receiptWithDet: StateFlow<ReceiptWithDetailView> = _receiptWithDet.asStateFlow()

    private val _showDialogPermissionRationale = MutableStateFlow(false)
    val showDialogPermissionRationale: StateFlow<Boolean> =
        _showDialogPermissionRationale.asStateFlow()

    fun setShowDialogPermissionRationale(show: Boolean) {
        _showDialogPermissionRationale.update { show }
    }

    fun getReceiptWithDetById(id: Int) {
        viewModelScope.launch {
            startLoading()
            getReceipts.getReceiptWithDetById(id).collect { receiptWithDet ->
                Log.d("TicketViewModel", "getReceiptWithDetById response = $receiptWithDet")
                _receiptWithDet.value = receiptWithDet
                stopLoading()
            }
        }
    }
}