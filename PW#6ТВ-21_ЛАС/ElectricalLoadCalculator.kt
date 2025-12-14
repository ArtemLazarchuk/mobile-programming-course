package com.example.lab_1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.*

/**
 * Калькулятор для розрахунку електричних навантажень методом впорядкованих діаграм
 * Лабораторна робота 6
 */
@Composable
fun ElectricalLoadCalculator() {
    // Типи ЕП (перші 8 - для ШР, останні 2 - крупні ЕП)
    val epTypes = remember {
        listOf(
            EPType("Шліфувальний верстат", 0.92, 0.9, 0.38, 4),
            EPType("Свердлильний верстат", 0.92, 0.9, 0.38, 2),
            EPType("Фугувальний верстат", 0.92, 0.9, 0.38, 4),
            EPType("Циркулярна пила", 0.92, 0.9, 0.38, 1),
            EPType("Прес", 0.92, 0.9, 0.38, 1),
            EPType("Полірувальний верстат", 0.92, 0.9, 0.38, 1),
            EPType("Фрезерний верстат", 0.92, 0.9, 0.38, 2),
            EPType("Вентилятор", 0.92, 0.9, 0.38, 1),
            EPType("Зварювальний трансформатор", 0.92, 0.9, 0.38, 2),
            EPType("Сушильна шафа", 0.92, 0.9, 0.38, 2)
        )
    }
    
    var epData by remember {
        mutableStateOf(epTypes.map { EPData(it, "", "", "") })
    }
    
    var result by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Розрахунок електричних навантажень", style = MaterialTheme.typography.headlineMedium)
        Text("Лабораторна робота 6", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
        Divider()

        Text("Вхідні дані", style = MaterialTheme.typography.titleLarge)
        
        epData.forEachIndexed { index, data ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${index + 1}. ${data.type.name}", style = MaterialTheme.typography.titleSmall)
                    Text("n=${data.type.n}, η=${data.type.eta}, cosφ=${data.type.cosPhi}, U=${data.type.u}кВ", 
                         style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(value = data.pn, onValueChange = { epData = epData.toMutableList().apply { this[index] = data.copy(pn = it) } },
                            label = { Text("Рн (кВт)") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.weight(1f))
                        OutlinedTextField(value = data.kv, onValueChange = { epData = epData.toMutableList().apply { this[index] = data.copy(kv = it) } },
                            label = { Text("Кв") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.weight(1f))
                        OutlinedTextField(value = data.tgPhi, onValueChange = { epData = epData.toMutableList().apply { this[index] = data.copy(tgPhi = it) } },
                            label = { Text("tgφ") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal), modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                epData = listOf(
                    EPData(epTypes[0], "20", "0.15", "1.33"), EPData(epTypes[1], "14", "0.12", "1.0"),
                    EPData(epTypes[2], "42", "0.15", "1.33"), EPData(epTypes[3], "36", "0.3", "1.52"),
                    EPData(epTypes[4], "20", "0.5", "0.75"), EPData(epTypes[5], "40", "0.2", "1.0"),
                    EPData(epTypes[6], "32", "0.2", "1.0"), EPData(epTypes[7], "20", "0.65", "0.75"),
                    EPData(epTypes[8], "100", "0.2", "3.0"), EPData(epTypes[9], "120", "0.8", "0.0")
                )
                errorMessage = null
            }, modifier = Modifier.weight(1f)) { Text("Приклад") }

            Button(onClick = {
                errorMessage = null
                val parsed = epData.mapIndexedNotNull { i, data ->
                    val pn = data.pn.toDoubleOrNull()
                    val kv = data.kv.toDoubleOrNull()
                    val tgPhi = data.tgPhi.toDoubleOrNull()
                    when {
                        pn == null || pn <= 0 -> { errorMessage = "Рн для ${data.type.name}"; null }
                        kv == null || kv < 0 || kv > 1 -> { errorMessage = "Кв для ${data.type.name}"; null }
                        tgPhi == null || tgPhi < 0 -> { errorMessage = "tgφ для ${data.type.name}"; null }
                        else -> ParsedEPData(data.type, pn, kv, tgPhi)
                    }
                }
                if (parsed.size == epData.size) {
                    result = calculateLoads(parsed)
                }
            }, modifier = Modifier.weight(1f)) { Text("Обчислити") }
        }

        errorMessage?.let {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                Text("Помилка: перевірте $it", color = MaterialTheme.colorScheme.onErrorContainer, modifier = Modifier.padding(16.dp))
            }
        }

        result?.let {
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Text(it, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

data class EPType(val name: String, val eta: Double, val cosPhi: Double, val u: Double, val n: Int)
data class EPData(val type: EPType, val pn: String, val kv: String, val tgPhi: String)
data class ParsedEPData(val type: EPType, val pn: Double, val kv: Double, val tgPhi: Double)

data class GroupResult(val kv: Double, val ne: Double, val kp: Double, val pp: Double, val qp: Double, val sp: Double, val ip: Double)

fun calculateLoads(epList: List<ParsedEPData>): String {
    // Розрахунок для ШР (перші 8 ЕП)
    val shrEPs = epList.take(8)
    val shrResult = calculateGroup(shrEPs, useKpForQ = false)
    
    // Розрахунок для цеху (всі ЕП)
    val workshopResult = calculateGroup(epList, useKpForQ = true)
    
    return buildString {
        appendLine("1.1. Груповий коефіцієнт використання для ШР1=ШР2=ШР3: ${String.format("%.4f", shrResult.kv)};")
        appendLine("1.2. Ефективна кількість ЕП для ШР1=ШР2=ШР3: ${shrResult.ne.toInt()};")
        appendLine("1.3. Розрахунковий коефіцієнт активної потужності для ШР1=ШР2=ШР3: ${String.format("%.2f", shrResult.kp)};")
        appendLine("1.4. Розрахункове активне навантаження для ШР1=ШР2=ШР3: ${String.format("%.2f", shrResult.pp)} кВт;")
        appendLine("1.5. Розрахункове реактивне навантаження для ШР1=ШР2=ШР3: ${String.format("%.3f", shrResult.qp)} квар.;")
        appendLine("1.6. Повна потужність для ШР1=ШР2=ШР3: ${String.format("%.4f", shrResult.sp)} кВ*А;")
        appendLine("1.7. Розрахунковий груповий струм для ШР1=ШР2=ШР3: ${String.format("%.2f", shrResult.ip)} А;")
        appendLine("1.8. Коефіцієнти використання цеху в цілому: ${String.format("%.2f", workshopResult.kv)};")
        appendLine("1.9. Ефективна кількість ЕП цеху в цілому: ${workshopResult.ne.toInt()};")
        appendLine("1.10. Розрахунковий коефіцієнт активної потужності цеху в цілому: ${String.format("%.1f", workshopResult.kp)};")
        appendLine("1.11. Розрахункове активне навантаження на шинах 0,38 кВ ТП: ${String.format("%.1f", workshopResult.pp)} кВт;")
        appendLine("1.12. Розрахункове реактивне навантаження на шинах 0,38 кВ ТП: ${String.format("%.1f", workshopResult.qp)} квар;")
        appendLine("1.13. Повна потужність на шинах 0,38 кВ ТП: ${String.format("%.0f", workshopResult.sp)} кВ*А;")
        appendLine("1.14. Розрахунковий груповий струм на шинах 0,38 кВ ТП: ${String.format("%.3f", workshopResult.ip)} А.")
    }
}

fun calculateGroup(epList: List<ParsedEPData>, useKpForQ: Boolean): GroupResult {
    val sumNPn = epList.sumOf { it.type.n * it.pn }
    val sumNPnKv = epList.sumOf { it.type.n * it.pn * it.kv }
    val sumNPnKvTgPhi = epList.sumOf { it.type.n * it.pn * it.kv * it.tgPhi }
    val sumNPn2 = epList.sumOf { it.type.n * it.pn * it.pn }
    
    // Груповий коефіцієнт використання: Кв = (Σ n·Pн·Кв) / (Σ n·Pн)
    val kv = if (sumNPn > 0) sumNPnKv / sumNPn else 0.0
    
    // Ефективна кількість ЕП: nе = (Σ n·Pн)² / (Σ n·Pн²)
    val ne = if (sumNPn2 > 0) (sumNPn * sumNPn) / sumNPn2 else 0.0
    
    // Розрахунковий коефіцієнт активної потужності з таблиці
    val kp = getKpFromTable(kv, ne)
    
    // Розрахункове активне навантаження: Рр = Kр · Σ(n·Pн·Кв)
    val pp = kp * sumNPnKv
    
    // Розрахункове реактивне навантаження
    // Для ШР: Qр = Σ(n·Pн·Кв·tgφ) (без Kр)
    // Для цеху: Qр = Kр · Σ(n·Pн·Кв·tgφ) (з Kр)
    // Згідно з 6.5: Q_p = K_p * K_B * P_H * tgφ = 0.7 × 657 = 459.9
    // де K_B * P_H * tgφ = Σ(n·Pн·Кв·tgφ) = 657
    val qp = if (useKpForQ) kp * sumNPnKvTgPhi else sumNPnKvTgPhi
    
    // Повна потужність: Sр = √(Рр² + Qр²)
    val sp = sqrt(pp * pp + qp * qp)
    
    // Розрахунковий груповий струм: Iр = Рр / Uн
    // Згідно з 6.7: I_p = P_p / U_H = 526.4 / 0.38 = 1385.26 А
    val u = epList.firstOrNull()?.type?.u ?: 0.38
    val ip = pp / u
    
    return GroupResult(kv, ne, kp, pp, qp, sp, ip)
}

fun getKpFromTable(kv: Double, ne: Double): Double {
    val neRounded = ne.roundToInt().coerceAtLeast(1)
    
    // Таблиця 6.3 для ШР (Кв <= 0.2)
    // Таблиця 6.4 для цеху (Кв > 0.2)
    return when {
        kv <= 0.1 -> 1.0
        kv <= 0.2 -> when {
            // Таблиця 6.3: для Кв=0.2, nе=15 → Kр=1.25
            neRounded <= 2 -> 1.0
            neRounded <= 5 -> 1.1
            neRounded <= 10 -> 1.15
            neRounded <= 15 -> 1.25
            neRounded <= 20 -> 1.25
            neRounded <= 30 -> 1.25
            else -> 1.25
        }
        kv <= 0.4 -> when {
            // Таблиця 6.4: для Кв=0.32, nе=56 → Kр=0.7
            neRounded <= 2 -> 1.0
            neRounded <= 5 -> 1.0
            neRounded <= 10 -> 1.0
            neRounded <= 20 -> 1.0
            neRounded <= 30 -> 1.0
            neRounded <= 50 -> 0.7
            neRounded <= 60 -> 0.7
            else -> 0.7
        }
        else -> when {
            neRounded <= 10 -> 1.0
            neRounded <= 20 -> 1.0
            neRounded <= 30 -> 1.0
            neRounded <= 50 -> 0.7
            else -> 0.7
        }
    }
}
