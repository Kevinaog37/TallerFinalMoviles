package com.kevin.tallerfinalk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.kevin.tallerfinalk.ui.view.MainScreen
import com.kevin.tallerfinalk.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel = ViewModelProvider(this).get(MainViewModel::class.java))
        }
    }
}
