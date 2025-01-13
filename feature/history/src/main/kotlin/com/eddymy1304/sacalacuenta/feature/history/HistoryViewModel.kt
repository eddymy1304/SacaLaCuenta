package com.eddymy1304.sacalacuenta.feature.history

import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.core.common.Utils
import com.eddymy1304.sacalacuenta.core.domain.GetReceipts
import com.eddymy1304.sacalacuenta.core.model.ReceiptWithDetail
import com.eddymy1304.sacalacuenta.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getTickets: GetReceipts
) : BaseViewModel() {

    private val _date = MutableStateFlow(Utils.getDate())
    val date: StateFlow<String> get() = _date

    private val _listReceiptWithDet = MutableStateFlow<List<ReceiptWithDetail>>(listOf())
    val listReceiptWithDet: StateFlow<List<ReceiptWithDetail>> get() = _listReceiptWithDet

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> get() = _showDatePicker

    private val _showAlertDialog = MutableStateFlow(false)
    val showAlertDialog: StateFlow<Boolean> get() = _showAlertDialog

    init {
        getTicketsByDate()
    }

    private fun getTicketsByDate() {
        viewModelScope.launch {
            startLoading()
            getTickets.getReceiptsByDate(_date.value).collect {
                _listReceiptWithDet.value = it
                stopLoading()
            }
        }
    }

    fun getTicketsByDate(date: String) {
        setDate(date)
        setShowDatePicker(false)
        getTicketsByDate()
    }

    fun setShowDatePicker(show: Boolean) {
        _showDatePicker.update { show }
    }

    fun setShowAlertDialog(show: Boolean) {
        _showAlertDialog.update { show }
    }

    fun setDate(date: String) {
        _date.update { date }
    }
}