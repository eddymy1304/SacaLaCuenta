package com.eddymy1304.sacalacuenta.ui.screens

import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.base.BaseViewModel
import com.eddymy1304.sacalacuenta.data.models.ReceiptWithDetailView
import com.eddymy1304.sacalacuenta.domain.GetReceipts
import com.eddymy1304.sacalacuenta.utils.Utils
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

    private val _listReceiptWithDet = MutableStateFlow<List<ReceiptWithDetailView>>(listOf())
    val listReceiptWithDet: StateFlow<List<ReceiptWithDetailView>> get() = _listReceiptWithDet

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