package com.kevin.tallerfinalk.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.kevin.tallerfinalk.model.CalculationModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    // Cambiar a StateFlow
    private val _selectedOption = MutableStateFlow("Selecciona una opción")
    val selectedOption: StateFlow<String> get() = _selectedOption

    private val _selectedButton = MutableStateFlow(0) // 0 = ninguno, 1 = Producto, 2 = Empleador, 3 = Empleado
    val selectedButton: StateFlow<Int> get() = _selectedButton

    // Función para manejar la selección de botón
    fun onButtonSelected(button: Int) {
        _selectedButton.value = button
        _selectedOption.value = "Selecciona una opción"  // Resetear la opción seleccionada
    }

    // Función para manejar la selección de opción en el dropdown
    fun onOptionSelected(option: String) {
        _selectedOption.value = option
    }

    // Obtener la configuración para la opción seleccionada
    fun getConfigurationForOption(option: String): CalculationModel.OptionConfiguration? {
        return when (_selectedButton.value) {
            1 -> CalculationModel.productConfigurations[option]
            2 -> CalculationModel.employerConfigurations[option]
            3 -> CalculationModel.employeeConfigurations[option]
            else -> null
        }
    }
}
