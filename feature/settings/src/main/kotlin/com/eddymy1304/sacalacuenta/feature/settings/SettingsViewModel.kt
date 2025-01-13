package com.eddymy1304.sacalacuenta.feature.settings

import androidx.lifecycle.viewModelScope
import com.eddymy1304.sacalacuenta.core.domain.GetUserName
import com.eddymy1304.sacalacuenta.core.domain.SaveUserName
import com.eddymy1304.sacalacuenta.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveUserName: SaveUserName,
    private val getUserName: GetUserName
) : BaseViewModel()  {

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getUserName().collect {
                _userName.value = it
            }
        }
    }
    fun saveUserName(name: String) {
        _userName.value = name
        viewModelScope.launch {
            saveUserName.invoke(name)
            getUserName().collect {
                if (it != name) _userName.value = it
            }
        }
    }

    fun setShowDialog(show: Boolean) {
        _showDialog.update { show }
    }
}