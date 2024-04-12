package com.example.sacalacuenta

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sacalacuenta.data.models.CuentaView
import com.example.sacalacuenta.data.models.CuentaWithDetalleView
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.data.models.Screen.TicketScreen
import com.example.sacalacuenta.domain.GetCuentas
import com.example.sacalacuenta.domain.SaveCuenta
import com.example.sacalacuenta.utils.UiText
import com.example.sacalacuenta.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveCuenta: SaveCuenta,
    private val getCuentas: GetCuentas
) : ViewModel() {

    private val _onClickIconShare = MutableStateFlow(false)
    val onClickIconShare: StateFlow<Boolean> get() = _onClickIconShare

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> get() = _showDatePicker

    private val _fecha = MutableStateFlow(Utils.getDate())
    val fecha: StateFlow<String> get() = _fecha

    private val _listCuentaWithDetalle = MutableStateFlow<List<CuentaWithDetalleView>>(listOf())
    val listCuentaWithDetalle: StateFlow<List<CuentaWithDetalleView>> get() = _listCuentaWithDetalle

    private val _cuentaWithDetalle = MutableStateFlow(CuentaWithDetalleView())
    val cuentaWithDetalle: StateFlow<CuentaWithDetalleView> get() = _cuentaWithDetalle

    private val _navTo = MutableStateFlow(Pair(false, ""))
    val navTo: StateFlow<Pair<Boolean, String>> get() = _navTo

    private val _nameUser = MutableStateFlow("")
    val nameUser: StateFlow<String> get() = _nameUser

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> get() = _showLoading

    private val _showMessage = MutableStateFlow<UiText>(UiText.Empty)
    val showMessage: StateFlow<UiText> get() = _showMessage

    private val _cuenta = MutableStateFlow(CuentaView())
    val cuenta: StateFlow<CuentaView> get() = _cuenta

    private val _listDetCuenta = MutableStateFlow(listOf(DetalleCuentaView()))
    val listDetCuenta: StateFlow<List<DetalleCuentaView>> get() = _listDetCuenta

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    init {
        Log.d("Eddycito", "MainViewModel init")
    }

    fun deleteDetalleCuenta(det: DetalleCuentaView) {
        _listDetCuenta.value = _listDetCuenta.value.minus(det)
    }

    fun addDetalleCuenta(detalle: DetalleCuentaView = DetalleCuentaView()) {
        _listDetCuenta.value = _listDetCuenta.value.plus(detalle)
    }

    fun updateTotal() {
        _total.value = _listDetCuenta.value.sumOf { it.total.value ?: 0.0 }
    }

    fun saveCuentaAndListDetCuenta(cuenta: CuentaView, listDetCuenta: List<DetalleCuentaView>) {

        if (!validateCuenta(cuenta, listDetCuenta)) return

        viewModelScope.launch {
            _showLoading.value = true
            saveCuenta(cuenta, listDetCuenta)
            _showLoading.value = false
            resetCuenta()
            getLastTicket()
            _navTo.value = Pair(true, TicketScreen.route)
        }
    }

    fun resetCuenta() {
        _cuenta.value = CuentaView()
        _listDetCuenta.value = listOf(DetalleCuentaView())
        _total.value = 0.0
    }

    private fun validateCuenta(
        cuenta: CuentaView,
        listDetCuenta: List<DetalleCuentaView>
    ): Boolean {
        return when {
            cuenta.title.value.isNullOrBlank() -> {
                _showMessage.value = UiText.StringResource(R.string.empty_field_title)
                false
            }

            cuenta.paymentMethod.value.isNullOrBlank() -> {
                _showMessage.value = UiText.StringResource(R.string.empty_field_payment_method)
                false
            }

            listDetCuenta.isEmpty() -> {
                _showMessage.value = UiText.StringResource(R.string.empty_list_det_cuenta)
                false
            }

            listDetCuenta.any { it.total.value == null || it.total.value == 0.00 } -> {
                _showMessage.value = UiText.StringResource(R.string.empty_field_total_det_cuenta)
                false
            }

            else -> true
        }
    }

    fun updateMessage(message: UiText) {
        _showMessage.value = message
    }

    fun getLastTicket() {
        viewModelScope.launch {
            _showLoading.value = true
            getCuentas.getLastCuentaWithDetalle().collect {
                _cuentaWithDetalle.value = it
                _showLoading.value = false
            }
        }
    }

    fun resetNavTo() {
        _navTo.value = Pair(false, "")
    }

    fun getAllTickets() {
        viewModelScope.launch {
            _showLoading.value = true
            getCuentas().collect {
                _listCuentaWithDetalle.value = it
                _showLoading.value = false
            }
        }
    }

    fun getTicketsByDate() {
        viewModelScope.launch {
            _showLoading.value = true
            delay(500L) // Simulate network delay()
            getCuentas.getCuentasByDate(_fecha.value).collect {
                _listCuentaWithDetalle.value = it
                _showLoading.value = false
            }
        }
    }

    fun updateCuentaWithDetalle(cuentaWithDetalle: CuentaWithDetalleView) {
        _cuentaWithDetalle.value = cuentaWithDetalle
    }

    fun updateShowDatePicker(show: Boolean) {
        _showDatePicker.value = show
    }

    fun updateFecha(fecha: String) {
        _fecha.value = fecha
    }

    fun updateOnClickIconShare(click: Boolean) {
        _onClickIconShare.value = click
    }
}