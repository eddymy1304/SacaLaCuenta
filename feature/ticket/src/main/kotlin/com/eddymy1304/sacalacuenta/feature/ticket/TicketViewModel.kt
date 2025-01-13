package com.eddymy1304.sacalacuenta.feature.ticket

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.core.domain.GetReceipts
import com.eddymy1304.sacalacuenta.core.model.ReceiptWithDetail
import com.eddymy1304.sacalacuenta.core.viewmodel.BaseViewModel
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

    private val _receiptWithDet = MutableStateFlow(ReceiptWithDetail())
    val receiptWithDet: StateFlow<ReceiptWithDetail> = _receiptWithDet.asStateFlow()

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