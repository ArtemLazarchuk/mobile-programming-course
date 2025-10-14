package com.example.lab_1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun EmissionsCalculatorTask() {
    var bCoal by remember { mutableStateOf("1096363") }
    var bOil by remember { mutableStateOf("70945") }
    var bGas by remember { mutableStateOf("84762") }

    var qCoal by remember { mutableStateOf("20.47") }
    var qOil by remember { mutableStateOf("39.48") }
    var qGas by remember { mutableStateOf("33.08") }

    var kCoal by remember { mutableStateOf("25.20") }
    var kOil by remember { mutableStateOf("0.15") }
    var kGas by remember { mutableStateOf("0.723") }

    var result by remember { mutableStateOf("") }

    // додаємо прокрутку та відступи
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // щоб усе влазило
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Введіть дані для кожного виду палива:",
            style = MaterialTheme.typography.titleMedium
        )

        // ======== ВУГІЛЛЯ ========
        Text("Вугілля", style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(
            value = bCoal,
            onValueChange = { bCoal = it },
            label = { Text("Обс'яг палива (т)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = qCoal,
            onValueChange = { qCoal = it },
            label = { Text("Нижча теплота згорання (МДж/м3)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = kCoal,
            onValueChange = { kCoal = it },
            label = { Text("Масовий вміст золи в паливі (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // ======== МАЗУТ ========
        Text("Мазут", style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(
            value = bOil,
            onValueChange = { bOil = it },
            label = { Text("Обс'яг палива (т)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = qOil,
            onValueChange = { qOil = it },
            label = { Text("Нижча теплота згорання (МДж/кг)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = kOil,
            onValueChange = { kOil = it },
            label = { Text("Масовий вміст золи в паливі (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // ======== ПРИРОДНИЙ ГАЗ ========
        Text("Природний газ", style = MaterialTheme.typography.titleSmall)
        OutlinedTextField(
            value = bGas,
            onValueChange = { bGas = it },
            label = { Text("Обс'яг палива (тис м³)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = qGas,
            onValueChange = { qGas = it },
            label = { Text("Об’ємна нижча теплота зг. (МДж/м3)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = kGas,
            onValueChange = { kGas = it },
            label = { Text("Густина (кг/м3)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // ======== КНОПКА + РЕЗУЛЬТАТ ========
        Button(
            onClick = {
                result = calculateEmissions(bCoal, bOil, bGas, qCoal, qOil, qGas, kCoal, kOil, kGas)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обрахувати")
        }

        if (result.isNotBlank()) {
            Text(result, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
fun calculateEmissions(
    bCoal: String,
    bOil: String,
    bGas: String,
    qCoal: String,
    qOil: String,
    qGas: String,
    kCoal: String,
    kOil: String,
    kGas: String
): String {
    // Безпечне перетворення тексту в числа
    val bC = bCoal.toDoubleOrNull() ?: 0.0
    val bO = bOil.toDoubleOrNull() ?: 0.0
    val bG = bGas.toDoubleOrNull() ?: 0.0

    val qC = qCoal.toDoubleOrNull() ?: 0.0
    val qO = qOil.toDoubleOrNull() ?: 0.0
    val qG = qGas.toDoubleOrNull() ?: 0.0

    val kC = kCoal.toDoubleOrNull() ?: 0.0
    val kO = kOil.toDoubleOrNull() ?: 0.0
    val kG = kGas.toDoubleOrNull() ?: 0.0

    // Переведення тис. т у кг, млн м³ у м³, якщо потрібно
    val colaK = ((1000000 / qC)*0.8*(kC/(100-1.5))*(1-0.985))
    val oilK = ((1000000 / qO)*1*(kO/(100-0))*(1-0.985))
    val coalEmission = (0.000001)*colaK*qC*bC
    val oilEmission = (0.000001)*oilK*qO*bO


    return """
        1.1 Показник емісії твердих частинок при спалюванні вугілля: 
         ${"%.2f".format(colaK)} г/ГДж
        
        1.2 Валовий викид при спалюванні вугілля: 
        ${"%.2f".format(coalEmission)} т.
        
        1.3 Показник емісії твердих частинок при спалюванні мазуту:
        Hc = ${"%.2f".format(oilK)} г/ГДж
        
        1.4 Валовий викид при спалюванні мазуту:
        Hг = ${"%.2f".format(oilEmission)} т.
        
        1.5 Показник емісії твердих частинок при спалюванні природного газу:
        0 г/ГДж
        
        1.6 Нижча теплота згоряння для сухої маси:
        0 т.
    """.trimIndent()
}
