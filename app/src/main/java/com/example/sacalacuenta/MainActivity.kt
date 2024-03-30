package com.example.sacalacuenta

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.sacalacuenta.ui.screens.MainScreen
import com.example.sacalacuenta.ui.theme.SacaLaCuentaTheme
import com.example.sacalacuenta.utils.UiText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SacaLaCuentaTheme {
                MainScreen(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel
                ) {
                    finish()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.showMessage.collect { message ->
                if (message != UiText.Empty) {
                    Toast.makeText(
                        this@MainActivity,
                        message.asString(this@MainActivity),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.updateMessage(UiText.Empty)
                }
            }
        }
    }
}
