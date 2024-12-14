package com.kevin.tallerfinalk.model

data class OptionConfiguration(
    val inputs: List<String>, // Lista de nombres de inputs
    val formula: (Map<String, Double>) -> Double // Fórmula para calcular el resultado
)
