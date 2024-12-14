package com.kevin.tallerfinalk.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kevin.tallerfinalk.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val selectedOption by viewModel.selectedOption.collectAsState()
    val selectedButton by viewModel.selectedButton.collectAsState()

    val optionsButton1 = listOf("Precio de venta con IVA", "Margen de ganancia", "Punto de equilibrio", "ROI del producto")
    val optionsButton2 = listOf("Costo total de nómina", "Provisiones sociales", "Aportes parafiscales", "Prestaciones sociales")
    val optionsButton3 = listOf("Salario neto", "Deducciones de nómina", "Horas extras", "Bonificaciones")

    val currentOptions = when (selectedButton) {
        1 -> optionsButton1
        2 -> optionsButton2
        3 -> optionsButton3
        else -> emptyList()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        ButtonSelection(
            onButtonClick = { viewModel.onButtonSelected(it) }
        )

        if (selectedButton != 0) {
            DropdownMenuComponent(
                selectedOption = selectedOption,
                options = currentOptions,
                onOptionSelected = { viewModel.onOptionSelected(it) }
            )
        }

        DynamicInputs(selectedOption = selectedOption, viewModel = viewModel)
    }
}

@Composable
fun ButtonSelection(onButtonClick: (Int) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Button(onClick = { onButtonClick(1) }) {
            Text("Producto")
        }
        Button(onClick = { onButtonClick(2) }) {
            Text("Empleador")
        }
        Button(onClick = { onButtonClick(3) }) {
            Text("Empleado")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuComponent(
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Seleccione una opción") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DynamicInputs(selectedOption: String, viewModel: MainViewModel) {
    val configuration = viewModel.getConfigurationForOption(selectedOption)

    if (configuration != null) {
        val inputValues = remember { mutableStateMapOf<String, String>() }
        val result = remember { mutableStateOf<Double?>(null) }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            // Crear inputs dinámicamente
            configuration.inputs.forEach { inputName ->
                TextField(
                    value = inputValues[inputName] ?: "",
                    onValueChange = { inputValues[inputName] = it },
                    label = { Text(inputName) },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )
            }

            // Botón para calcular
            Button(
                onClick = {
                    try {
                        val numericInputs = inputValues.mapValues { it.value.toDouble() }
                        result.value = configuration.formula(numericInputs)
                    } catch (e: Exception) {
                        result.value = null // Manejo de errores si los inputs no son válidos
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Calcular")
            }

            // Mostrar resultado si existe
            result.value?.let {
                Text(
                    text = "Resultado: ${"%.2f".format(it)}",
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        Text("Por favor, selecciona una opción válida.", modifier = Modifier.padding(16.dp))
    }
}
