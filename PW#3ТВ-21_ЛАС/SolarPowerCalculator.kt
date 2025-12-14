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
import kotlin.math.*

@Composable
fun SolarPowerCalculatorTask() {
    var pc by remember { mutableStateOf("5") } // Середньодобова потужність (МВт)
    var sigma1 by remember { mutableStateOf("1") } // Середнє квадратичне відхилення (МВт)
    var sigma2 by remember { mutableStateOf("0.25") } // Зменшене середнє квадратичне відхилення (МВт)
    var b by remember { mutableStateOf("7") } // Вартість електроенергії (грн/кВт-год)

    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Розрахунок прибутку від вдосконалення системи прогнозування",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = pc,
            onValueChange = { pc = it },
            label = { Text("Середньодобова потужність Pc (МВт)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sigma1,
            onValueChange = { sigma1 = it },
            label = { Text("Середнє квадратичне відхилення σ₁ (МВт)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sigma2,
            onValueChange = { sigma2 = it },
            label = { Text("Зменшене середнє квадратичне відхилення σ₂ (МВт)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = b,
            onValueChange = { b = it },
            label = { Text("Вартість електроенергії B (грн/кВт-год)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                result = calculateSolarProfit(pc, sigma1, sigma2, b)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обрахувати")
        }

        if (result.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = result,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun calculateSolarProfit(
    pcStr: String,
    sigma1Str: String,
    sigma2Str: String,
    bStr: String
): String {
    val pc = pcStr.toDoubleOrNull() ?: 0.0
    val sigma1 = sigma1Str.toDoubleOrNull() ?: 0.0
    val sigma2 = sigma2Str.toDoubleOrNull() ?: 0.0
    val b = bStr.toDoubleOrNull() ?: 0.0

    if (pc <= 0 || sigma1 <= 0 || sigma2 <= 0 || b <= 0) {
        return "Помилка: всі значення повинні бути більше нуля"
    }

    // Діапазон без штрафів: якщо похибка прогнозу не перевищує δ = 5%
    // Це відповідає діапазону Pc ± 5% від Pc
    val delta = pc * 0.05 // 5% від середньодобової потужності
    val lowerBound = pc - delta
    val upperBound = pc + delta

    // Розрахунок для σ1
    val deltaW1 = calculateNormalProbability(pc, sigma1, lowerBound, upperBound)
    val w1 = pc * 24 * deltaW1 // Енергія без небалансів (МВт-год)
    val w2 = pc * 24 * (1 - deltaW1) // Енергія з небалансами (МВт-год)
    val profit1 = w1 * b * 1000 // Прибуток (тис. грн) - переводимо МВт-год в кВт-год
    val penalty1 = w2 * b * 1000 // Штраф (тис. грн)
    val netResult1 = profit1 - penalty1 // Чистий результат

    // Розрахунок для σ2
    val deltaW2 = calculateNormalProbability(pc, sigma2, lowerBound, upperBound)
    val w3 = pc * 24 * deltaW2 // Енергія без небалансів (МВт-год)
    val w4 = pc * 24 * (1 - deltaW2) // Енергія з небалансами (МВт-год)
    val profit2 = w3 * b * 1000 // Прибуток (тис. грн)
    val penalty2 = w4 * b * 1000 // Штраф (тис. грн)
    val netResult2 = profit2 - penalty2 // Чистий результат

    // Прибуток від вдосконалення
    val improvementProfit = netResult2 - netResult1

    return buildString {
        appendLine("═══════════════════════════════════")
        appendLine("РОЗРАХУНКИ ДЛЯ ПОЧАТКОВОЇ СИСТЕМИ (σ₁ = ${"%.2f".format(sigma1)} МВт)")
        appendLine("═══════════════════════════════════")
        appendLine()
        appendLine("Діапазон без штрафів: ${"%.2f".format(lowerBound)} - ${"%.2f".format(upperBound)} МВт")
        appendLine()
        appendLine("Частка енергії без небалансів δw₁ = ${"%.1f".format(deltaW1 * 100)}%")
        appendLine()
        appendLine("За ${"%.1f".format(deltaW1 * 100)}% електроенергії:")
        appendLine("  W₁ = Pc × 24 × δw₁ = ${"%.2f".format(pc)} × 24 × ${"%.2f".format(deltaW1)} = ${"%.2f".format(w1)} МВт-год")
        appendLine("  Прибуток П₁ = W₁ × B = ${"%.2f".format(w1)} × ${"%.2f".format(b)} × 1000 = ${"%.2f".format(profit1)} тис. грн")
        appendLine()
        appendLine("За ${"%.1f".format((1 - deltaW1) * 100)}% енергії:")
        appendLine("  W₂ = Pc × 24 × (1 - δw₁) = ${"%.2f".format(pc)} × 24 × ${"%.2f".format(1 - deltaW1)} = ${"%.2f".format(w2)} МВт-год")
        appendLine("  Штраф Ш₁ = W₂ × B = ${"%.2f".format(w2)} × ${"%.2f".format(b)} × 1000 = ${"%.2f".format(penalty1)} тис. грн")
        appendLine()
        appendLine("═══════════════════════════════════")
        appendLine("ВИСНОВОК ДЛЯ ПОЧАТКОВОЇ СИСТЕМИ:")
        if (netResult1 >= 0) {
            appendLine("Електростанція працює з прибутком: ${"%.2f".format(netResult1)} тис. грн")
        } else {
            appendLine("Електростанція є нерентабельною і працює в збиток: ${"%.2f".format(abs(netResult1))} тис. грн")
        }
        appendLine("═══════════════════════════════════")
        appendLine()
        appendLine()
        appendLine("═══════════════════════════════════")
        appendLine("РОЗРАХУНКИ ДЛЯ ВДОСКОНАЛЕНОЇ СИСТЕМИ (σ₂ = ${"%.2f".format(sigma2)} МВт)")
        appendLine("═══════════════════════════════════")
        appendLine()
        appendLine("Діапазон без штрафів: ${"%.2f".format(lowerBound)} - ${"%.2f".format(upperBound)} МВт")
        appendLine()
        appendLine("Частка енергії без небалансів δw₂ = ${"%.1f".format(deltaW2 * 100)}%")
        appendLine()
        appendLine("За ${"%.1f".format(deltaW2 * 100)}% електроенергії:")
        appendLine("  W₃ = Pc × 24 × δw₂ = ${"%.2f".format(pc)} × 24 × ${"%.2f".format(deltaW2)} = ${"%.2f".format(w3)} МВт-год")
        appendLine("  Прибуток П₂ = W₃ × B = ${"%.2f".format(w3)} × ${"%.2f".format(b)} × 1000 = ${"%.2f".format(profit2)} тис. грн")
        appendLine()
        appendLine("За ${"%.1f".format((1 - deltaW2) * 100)}% енергії:")
        appendLine("  W₄ = Pc × 24 × (1 - δw₂) = ${"%.2f".format(pc)} × 24 × ${"%.2f".format(1 - deltaW2)} = ${"%.2f".format(w4)} МВт-год")
        appendLine("  Штраф Ш₂ = W₄ × B = ${"%.2f".format(w4)} × ${"%.2f".format(b)} × 1000 = ${"%.2f".format(penalty2)} тис. грн")
        appendLine()
        appendLine("═══════════════════════════════════")
        appendLine("ВИСНОВОК ДЛЯ ВДОСКОНАЛЕНОЇ СИСТЕМИ:")
        if (netResult2 >= 0) {
            appendLine("Електростанція працює з прибутком: ${"%.2f".format(netResult2)} тис. грн")
        } else {
            appendLine("Електростанція працює в збиток: ${"%.2f".format(abs(netResult2))} тис. грн")
        }
        appendLine("═══════════════════════════════════")
    }
}

/**
 * Обчислює ймовірність того, що значення нормального розподілу
 * знаходиться в діапазоні [lowerBound, upperBound]
 */
fun calculateNormalProbability(
    mean: Double,
    stdDev: Double,
    lowerBound: Double,
    upperBound: Double
): Double {
    // Використовуємо функцію помилки (erf) для обчислення інтегралу нормального розподілу
    // P(a < X < b) = 0.5 * [erf((b-μ)/(σ√2)) - erf((a-μ)/(σ√2))]
    
    val z1 = (lowerBound - mean) / (stdDev * sqrt(2.0))
    val z2 = (upperBound - mean) / (stdDev * sqrt(2.0))
    
    return 0.5 * (erf(z2) - erf(z1))
}

/**
 * Функція помилки (error function) - наближення через ряд
 * erf(x) = (2/√π) ∫[0 to x] e^(-t²) dt
 */
fun erf(x: Double): Double {
    // Використовуємо наближення Абрамовиця та Стігана
    // для швидкого та точного обчислення
    val a1 = 0.254829592
    val a2 = -0.284496736
    val a3 = 1.421413741
    val a4 = -1.453152027
    val a5 = 1.061405429
    val p = 0.3275911
    
    val sign = if (x < 0) -1.0 else 1.0
    val absX = abs(x)

    val t = 1.0 / (1.0 + p * absX)
    val y = 1.0 - (((((a5 * t + a4) * t) + a3) * t + a2) * t + a1) * t * exp(-absX * absX)
    
    return sign * y
}

