package com.eddymy1304.sacalacuenta

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.base.BaseViewModel
import com.eddymy1304.sacalacuenta.data.models.Screen.ScreenReceipt
import com.eddymy1304.sacalacuenta.domain.GetUserName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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

    private val _title = MutableStateFlow(ScreenReceipt.title)
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