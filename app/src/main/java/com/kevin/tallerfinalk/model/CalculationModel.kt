package com.kevin.tallerfinalk.model

// Modelo para las configuraciones de cálculos
object CalculationModel {

    // Estructura para definir las configuraciones de cada opción
    data class OptionConfiguration(
        val inputs: List<String>, // Lista de nombres de inputs
        val formula: (Map<String, Double>) -> Double // Fórmula para calcular el resultado
    )

    // Configuraciones del botón "Producto"
    val productConfigurations = mapOf(
        "Precio de venta con IVA" to OptionConfiguration(
            inputs = listOf("Precio base"),
            formula = { inputs -> inputs["Precio base"]!! * 1.19 }
        ),
        "Margen de ganancia" to OptionConfiguration(
            inputs = listOf("Precio venta", "Costo"),
            formula = { inputs -> ((inputs["Precio venta"]!! - inputs["Costo"]!!) / inputs["Precio venta"]!!) * 100 }
        ),
        "Punto de equilibrio" to OptionConfiguration(
            inputs = listOf("Costos fijos", "Precio venta unitario", "Costo variable unitario"),
            formula = { inputs ->
                inputs["Costos fijos"]!! / (inputs["Precio venta unitario"]!! - inputs["Costo variable unitario"]!!)
            }
        ),
        "ROI del producto" to OptionConfiguration(
            inputs = listOf("Ingresos", "Inversion"),
            formula = { inputs -> ((inputs["Ingresos"]!! - inputs["Inversion"]!!) / inputs["Inversion"]!!) * 100 }
        )
    )

    // Configuraciones del botón "Empleador"
    val employerConfigurations = mapOf(
        "Costo total de nómina" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs -> inputs["Salario base"]!! * 1.555 }
        ),
        "Provisiones sociales" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs -> inputs["Salario base"]!! * 0.2183 }
        ),
        "Aportes parafiscales" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs -> inputs["Salario base"]!! * 0.09 }
        ),
        "Prestaciones sociales" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs -> inputs["Salario base"]!! * 0.205 }
        )
    )

    // Configuraciones del botón "Empleado"
    val employeeConfigurations = mapOf(
        "Salario neto" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs ->
                val salarioBase = inputs["Salario base"]!!
                salarioBase * 0.92 // Salario base menos el 8%
            }
        ),
        "Deducciones de nómina" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs ->
                val salarioBase = inputs["Salario base"]!!
                salarioBase * 0.08 // Deducciones del 8% (8% del salario base)
            }
        ),
        "Horas extras" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs ->
                val salarioBase = inputs["Salario base"]!!
                // Calcular cada tipo de hora extra
                val horaExtraDiurna = (salarioBase / 240) * 1.25
                val horaExtraNocturna = (salarioBase / 240) * 1.75
                val horaExtraDominicalFestiva = (salarioBase / 240) * 2

                // Devolver la suma de las horas extras
                horaExtraDiurna + horaExtraNocturna + horaExtraDominicalFestiva
            }
        ),
        "Bonificaciones" to OptionConfiguration(
            inputs = listOf("Salario base"),
            formula = { inputs -> inputs["Salario base"]!! }
        )
    )
}
