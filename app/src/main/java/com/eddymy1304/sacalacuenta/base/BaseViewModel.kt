package com.eddymy1304.sacalacuenta.base

import androidx.lifecycle.ViewModel
import com.eddymy1304.sacalacuenta.utils.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewModel : ViewModel() {

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading.asStateFlow()

    private val _showMessage = MutableStateFlow<UiText>(UiText.Empty)
    val showMessage: StateFlow<UiText> = _showMessage.asStateFlow()

    private fun setLoading(isLoading: Boolean) {
        _showLoading.update { isLoading }
    }

    fun startLoading() {
        setLoading(true)
    }

    fun stopLoading() {
        setLoading(false)
    }

    fun setMessage(message: UiText) {
        _showMessage.update { message }
    }
}