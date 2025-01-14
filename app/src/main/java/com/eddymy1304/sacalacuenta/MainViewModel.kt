package com.eddymy1304.sacalacuenta

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.core.domain.GetUserName
import com.eddymy1304.sacalacuenta.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.eddymy1304.sacalacuenta.feature.receipt.R as receiptR

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserName: GetUserName
) : BaseViewModel() {

    private val _showBottomNav = MutableStateFlow(true)
    val showBottomNav: StateFlow<Boolean> = _showBottomNav.asStateFlow()

    private val _showActions = MutableStateFlow(true)
    val showActions: StateFlow<Boolean> = _showActions.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _title = MutableStateFlow(receiptR.string.title_receipt)
    val title: StateFlow<Int> = _title.asStateFlow()


    init {
        Log.d("MainViewModel", "MainViewModel init")
        getUser()
    }

    fun configScreen(
        @StringRes title: Int,
        showActions: Boolean = true,
        showBottomNav: Boolean = true
    ) {
        _title.value = title
        _showActions.value = showActions
        _showBottomNav.value = showBottomNav
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserName().collect {
                _userName.value = it
            }
        }
    }
}