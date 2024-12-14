package com.kevin.tallerfinalk.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.kevin.tallerfinalk.model.OptionConfiguration

class MainViewModel : ViewModel() {
    var selectedButton = mutableStateOf(0)
        private set

    var selectedOption = mutableStateOf("Selecciona una opción")
        private set

    val inputValues = mutableStateOf<Map<String, String>>(emptyMap())
    var result = mutableStateOf<Double?>(null)
        private set

    // Configuraciones dinámicas (simples para este ejemplo)
    private val productConfigurations = mapOf(
        "Precio de venta con IVA" to OptionConfiguration(
            inputs = listOf("Precio base"),
            formula = { inputs -> inputs["Precio base"]!! * 1.19 }
        )
    )

    fun onButtonClicked(button: Int) {
        selectedButton.value = button
        selectedOption.value = "Selecciona una opción"
    }

    fun onOptionSelected(option: String) {
        selectedOption.value = option
    }

    fun onInputValueChange(key: String, value: String) {
        inputValues.value = inputValues.value.toMutableMap().apply {
            put(key, value)
        }
    }

    fun calculateResult() {
        result.value = try {
            val numericInputs = inputValues.value.mapValues { it.value.toDouble() }
            productConfigurations[selectedOption.value]?.formula?.invoke(numericInputs)
        } catch (e: Exception) {
            null
        }
    }
}
