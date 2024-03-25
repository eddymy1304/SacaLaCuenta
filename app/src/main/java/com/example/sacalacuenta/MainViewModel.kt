package com.example.sacalacuenta

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sacalacuenta.data.models.CuentaView
import com.example.sacalacuenta.data.models.DetalleCuentaView
import com.example.sacalacuenta.domain.GetCuentas
import com.example.sacalacuenta.domain.SaveCuenta
import com.example.sacalacuenta.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val saveCuenta: SaveCuenta,
    private val getCuentas: GetCuentas
) : ViewModel() {

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

    fun deleteDetalleCuenta(det: DetalleCuentaView) {
        _listDetCuenta.value = _listDetCuenta.value.minus(det)
    }

    fun addDetalleCuenta(detalle: DetalleCuentaView = DetalleCuentaView()) {
        _listDetCuenta.value = _listDetCuenta.value.plus(detalle)
    }

    fun updateCuenta(cuenta: CuentaView) {
        _cuenta.value = cuenta.copy()
    }

    fun updateCurrentDetCuenta(position: Int, det: DetalleCuentaView) {
        Log.d(
            "UpdateCurrentDetCuenta", """
            position: $position
            det: $det
            listDetCuenta: ${_listDetCuenta.value}
        """.trimIndent()
        )
        val listUpdate = _listDetCuenta.value.toMutableList()
        listUpdate[position] = det
        _listDetCuenta.value = listUpdate
        Log.d(
            "UpdateCurrentDetCuenta", """
            listUpdate: $listUpdate
            listDetCuenta: ${_listDetCuenta.value}
        """.trimIndent()
        )
        updateTotal()
    }

    private fun updateTotal() {
        _total.value = _listDetCuenta.value.sumOf { it.total ?: 0.0 }
    }

    fun saveCuentaAndListDetCuenta(cuenta: CuentaView, listDetCuenta: List<DetalleCuentaView>) {
        _showLoading.value = true
        viewModelScope.launch {
            saveCuenta(cuenta, listDetCuenta)
            _showLoading.value = false

            getCuentas().collect {
                Log.d(
                    "MainViewModel", """
                    cuentas = $it
                """.trimIndent()
                )
            }
        }
    }
}