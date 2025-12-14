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
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun PowerSystemCalculatorTask() {
    // ========== ЗАВДАННЯ 1 ==========
    var sm by remember { mutableStateOf("") }
    var unom by remember { mutableStateOf("") }
    var result1 by remember { mutableStateOf("") }

    // ========== ЗАВДАННЯ 2 ==========
    var ucn by remember { mutableStateOf("") }
    var sk by remember { mutableStateOf("") }
    var uk by remember { mutableStateOf("") }
    var snomT by remember { mutableStateOf("") }
    var result2 by remember { mutableStateOf("") }

    // ========== ЗАВДАННЯ 3 ==========
    var ukMax by remember { mutableStateOf("") }
    var uvN by remember { mutableStateOf("") }
    var snomT3 by remember { mutableStateOf("") }
    var unN by remember { mutableStateOf("") }
    var rcMin by remember { mutableStateOf("") }
    var result3 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ========== ЗАВДАННЯ 1 ==========
        Text(
            "Завдання 1",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = sm,
            onValueChange = { sm = it },
            label = { Text("Sm (МВА)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = unom,
            onValueChange = { unom = it },
            label = { Text("U_nom (кВ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                result1 = calculateTask1(sm, unom)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обрахувати завдання 1")
        }

        if (result1.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = result1,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // ========== ЗАВДАННЯ 2 ==========
        Text(
            "Завдання 2",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = ucn,
            onValueChange = { ucn = it },
            label = { Text("Ucn (кВ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sk,
            onValueChange = { sk = it },
            label = { Text("Sk (МВА)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uk,
            onValueChange = { uk = it },
            label = { Text("Uk (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = snomT,
            onValueChange = { snomT = it },
            label = { Text("Snom_t (МВА)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                result2 = calculateTask2(ucn, sk, uk, snomT)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обрахувати завдання 2")
        }

        if (result2.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = result2,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // ========== ЗАВДАННЯ 3 ==========
        Text(
            "Завдання 3",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = ukMax,
            onValueChange = { ukMax = it },
            label = { Text("Uk_max (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = uvN,
            onValueChange = { uvN = it },
            label = { Text("Uv_n (кВ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = snomT3,
            onValueChange = { snomT3 = it },
            label = { Text("Snom_t (МВА)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = unN,
            onValueChange = { unN = it },
            label = { Text("Un_n (кВ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rcMin,
            onValueChange = { rcMin = it },
            label = { Text("Rc_min (Ом)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                result3 = calculateTask3(ukMax, uvN, snomT3, unN, rcMin)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Обрахувати завдання 3")
        }

        if (result3.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = result3,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

fun calculateTask1(smStr: String, unomStr: String): String {
    val Sm = smStr.toDoubleOrNull()
    val U_nom = unomStr.toDoubleOrNull()

    if (Sm == null || U_nom == null || U_nom == 0.0) {
        return "Будь ласка, введіть коректні значення."
    }

    // Константи
    val t_f = 2.5 // Час відключення, с
    val Jek = 1.4 // Економічна щільність струму, А/мм²
    val Ik = 2500.0 // Струм короткого замикання, А
    val Ct = 92.0 // Коефіцієнт термічної стійкості, А·с^0.5/мм²

    // Розрахунок робочого струму для одного кабеля
    // Sm / 2 - потужність на один кабель (двотрансформаторна підстанція)
    val Im = (Sm / 2) / (sqrt(3.0) * U_nom)
    val ImFormatted = String.format("%.2f", Im).toDouble()

    // Струм післяаварійного режиму (коли один кабель відключений)
    val Im_pa = 2 * ImFormatted
    val ImPaFormatted = String.format("%.2f", Im_pa).toDouble()

    // Економічний переріз кабеля
    val Sek = ImFormatted / Jek
    val SekFormatted = String.format("%.2f", Sek).toDouble()

    // Мінімальний переріз за умовою термічної стійкості
    val S_min = (Ik * sqrt(t_f)) / Ct
    val SMinFormatted = String.format("%.2f", S_min).toDouble()

    // Вибір перерізу (більший з економічного та мінімального)
    val S_selected = maxOf(SekFormatted, SMinFormatted)
    val SSelectedFormatted = String.format("%.2f", S_selected).toDouble()

    return buildString {
        appendLine("═══════════════════════════════════")
        appendLine("РЕЗУЛЬТАТИ РОЗРАХУНКУ ЗАВДАННЯ 1")
        appendLine("═══════════════════════════════════")
        appendLine()
        appendLine("1. Розрахунковий струм:")
        appendLine("   Іm = (Sm/2) / (√3 × U_nom) = ${"%.2f".format(ImFormatted)} А")
        appendLine("   Іm*па = 2 × Іm = ${"%.2f".format(ImPaFormatted)} А")
        appendLine()
        appendLine("2. Економічний переріз:")
        appendLine("   Sек = Іm / Jек = ${"%.2f".format(SekFormatted)} мм²")
        appendLine()
        appendLine("3. Термічна стійкість:")
        appendLine("   S_min = (Iк × √t_f) / Ct = ${"%.2f".format(SMinFormatted)} мм²")
        appendLine()
        appendLine("4. Вибір перерізу кабеля:")
        appendLine("   Sек = ${"%.2f".format(SekFormatted)} мм²")
        appendLine("   S_min = ${"%.2f".format(SMinFormatted)} мм²")
        if (SekFormatted >= SMinFormatted) {
            appendLine("   Обрано: S = ${"%.2f".format(SSelectedFormatted)} мм²")
        } else {
            appendLine("   Обрано: S = ${"%.2f".format(SSelectedFormatted)} мм²")
        }
        appendLine("═══════════════════════════════════")
    }
}

fun calculateTask2(ucnStr: String, skStr: String, ukStr: String, snomTStr: String): String {
    try {
        val UcnVal = ucnStr.toDoubleOrNull()
        val SkVal = skStr.toDoubleOrNull()
        val UkVal = ukStr.toDoubleOrNull()
        val Snom_tVal = snomTStr.toDoubleOrNull()

        if (UcnVal == null || SkVal == null || UkVal == null || Snom_tVal == null) {
            return "Помилка: перевірте введені дані!"
        }

        if (SkVal == 0.0 || Snom_tVal == 0.0) {
            return "Помилка: Sk та Snom_t не можуть бути нульовими!"
        }

        // Розрахунок опору системи
        val Xc = (UcnVal.pow(2)) / SkVal
        val XcFormatted = String.format("%.3f", Xc).toDouble()

        // Розрахунок опору трансформатора
        val Xt = (UkVal * (UcnVal.pow(2))) / (100 * Snom_tVal)
        val XtFormatted = String.format("%.3f", Xt).toDouble()

        // Сумарний опір
        val X_all = XcFormatted + XtFormatted
        val XAllFormatted = String.format("%.3f", X_all).toDouble()

        // Початкове діюче значення струму трифазного КЗ
        val Ipo = UcnVal / (sqrt(3.0) * XAllFormatted)
        val IpoFormatted = String.format("%.3f", Ipo).toDouble()

        return buildString {
            appendLine("═══════════════════════════════════")
            appendLine("РЕЗУЛЬТАТИ РОЗРАХУНКУ ЗАВДАННЯ 2")
            appendLine("═══════════════════════════════════")
            appendLine()
            appendLine("1. Опори елементів заступної схеми:")
            appendLine("   Xc = Ucn² / Sk = ${"%.3f".format(XcFormatted)} Ом")
            appendLine("   Xt = (Uk% / 100) × (Ucn² / Snom_t) = ${"%.3f".format(XtFormatted)} Ом")
            appendLine()
            appendLine("2. Сумарний опір для точки К1:")
            appendLine("   XΣ = Xc + Xt = ${"%.3f".format(XAllFormatted)} Ом")
            appendLine()
            appendLine("3. Початкове діюче значення струму трифазного КЗ:")
            appendLine("   Iп₀ = Ucn / (√3 × XΣ) = ${"%.3f".format(IpoFormatted)} кА")
            appendLine("═══════════════════════════════════")
        }
    } catch (e: Exception) {
        return "Помилка: перевірте введені дані!"
    }
}

fun calculateTask3(
    ukMaxStr: String,
    uvNStr: String,
    snomT3Str: String,
    unNStr: String,
    rcMinStr: String
): String {
    try {
        val Uk_max = ukMaxStr.toDoubleOrNull()
        val Uv_n = uvNStr.toDoubleOrNull()
        val Snom_t = snomT3Str.toDoubleOrNull()
        val Un_n = unNStr.toDoubleOrNull()
        val Rc_min = rcMinStr.toDoubleOrNull()

        if (Uk_max == null || Uv_n == null || Snom_t == null || Un_n == null || Rc_min == null) {
            return "Помилка: перевірте введені дані!"
        }

        if (Snom_t == 0.0 || Uv_n == 0.0 || Un_n == 0.0) {
            return "Помилка: Snom_t, Uv_n та Un_n не можуть бути нульовими!"
        }

        // Константи
        val l = 12.37 // км
        val Rc_n = 10.65 // Ом
        val Ro = 0.64
        val Xc_n = 24.02 // Ом
        val Xo = 0.363
        val Xc_min = 65.68 // Ом

        // Розрахунок реактивного опору трансформатора
        val Xt = (Uk_max * Uv_n.pow(2.0)) / (100.0 * Snom_t)
        val XtFormatted = String.format("%.3f", Xt).toDouble()

        // Опори на шинах 10 кВ, приведені до напруги 110 кВ
        val Rsh = Rc_n
        val Xsh = Xc_n + XtFormatted
        val Zsh = sqrt(Rsh.pow(2.0) + Xsh.pow(2.0))
        val RshFormatted = String.format("%.3f", Rsh).toDouble()
        val XshFormatted = String.format("%.3f", Xsh).toDouble()
        val ZshFormatted = String.format("%.3f", Zsh).toDouble()

        val Rsh_min = Rc_min
        val Xsh_min = Xc_min + XtFormatted
        val Zsh_min = sqrt(Rsh_min.pow(2.0) + Xsh_min.pow(2.0))
        val RshMinFormatted = String.format("%.3f", Rsh_min).toDouble()
        val XshMinFormatted = String.format("%.3f", Xsh_min).toDouble()
        val ZshMinFormatted = String.format("%.3f", Zsh_min).toDouble()

        // Струми КЗ на шинах 10 кВ, приведені до напруги 110 кВ
        val Ish_3 = (Uv_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZshFormatted)
        val Ish_2 = Ish_3 * (sqrt(3.0) / 2.0)
        val Ish_3_min = (Uv_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZshMinFormatted)
        val Ish_2_min = Ish_3_min * (sqrt(3.0) / 2.0)
        val Ish3Formatted = String.format("%.3f", Ish_3).toDouble()
        val Ish2Formatted = String.format("%.3f", Ish_2).toDouble()
        val Ish3MinFormatted = String.format("%.3f", Ish_3_min).toDouble()
        val Ish2MinFormatted = String.format("%.3f", Ish_2_min).toDouble()

        // Коефіцієнт приведення
        val Kpr = Un_n.pow(2.0) / Uv_n.pow(2.0)
        val KprFormatted = String.format("%.3f", Kpr).toDouble()

        // Опори на шинах 10 кВ в нормальному та мінімальному режимах
        val Rsh_n = RshFormatted * KprFormatted
        val Xsh_n = XshFormatted * KprFormatted
        val Zsh_n = sqrt(Rsh_n.pow(2.0) + Xsh_n.pow(2.0))
        val RshNFormatted = String.format("%.3f", Rsh_n).toDouble()
        val XshNFormatted = String.format("%.3f", Xsh_n).toDouble()
        val ZshNFormatted = String.format("%.3f", Zsh_n).toDouble()

        val Rsh_n_min = RshMinFormatted * KprFormatted
        val Xsh_n_min = XshMinFormatted * KprFormatted
        val Zsh_n_min = sqrt(Rsh_n_min.pow(2.0) + Xsh_n_min.pow(2.0))
        val RshNMinFormatted = String.format("%.3f", Rsh_n_min).toDouble()
        val XshNMinFormatted = String.format("%.3f", Xsh_n_min).toDouble()
        val ZshNMinFormatted = String.format("%.3f", Zsh_n_min).toDouble()

        // Дійсні струми КЗ на шинах 10 кВ
        val Ish_n_3 = (Un_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZshNFormatted)
        val Ish_n_2 = Ish_n_3 * (sqrt(3.0) / 2.0)
        val Ish_n_3_min = (Un_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZshNMinFormatted)
        val Ish_n_2_min = Ish_n_3_min * (sqrt(3.0) / 2.0)
        val IshN3Formatted = String.format("%.3f", Ish_n_3).toDouble()
        val IshN2Formatted = String.format("%.3f", Ish_n_2).toDouble()
        val IshN3MinFormatted = String.format("%.3f", Ish_n_3_min).toDouble()
        val IshN2MinFormatted = String.format("%.3f", Ish_n_2_min).toDouble()

        // Опори лінії
        val Rl = l * Ro
        val Xl = l * Xo
        val RlFormatted = String.format("%.3f", Rl).toDouble()
        val XlFormatted = String.format("%.3f", Xl).toDouble()

        // Опори в точці 10
        val Rall_n = RlFormatted + RshNFormatted
        val Xall_n = XlFormatted + XshNFormatted
        val Zall_n = sqrt(Rall_n.pow(2.0) + Xall_n.pow(2.0))
        val RallNFormatted = String.format("%.3f", Rall_n).toDouble()
        val XallNFormatted = String.format("%.3f", Xall_n).toDouble()
        val ZallNFormatted = String.format("%.3f", Zall_n).toDouble()

        val Rall_n_min = RlFormatted + RshNMinFormatted
        val Xall_n_min = XlFormatted + XshNMinFormatted
        val Zall_n_min = sqrt(Rall_n_min.pow(2.0) + Xall_n_min.pow(2.0))
        val RallNMinFormatted = String.format("%.3f", Rall_n_min).toDouble()
        val XallNMinFormatted = String.format("%.3f", Xall_n_min).toDouble()
        val ZallNMinFormatted = String.format("%.3f", Zall_n_min).toDouble()

        // Струми КЗ в точці 10
        val Il_n_3 = (Un_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZallNFormatted)
        val Il_n_2 = Il_n_3 * (sqrt(3.0) / 2.0)
        val Il_n_3_min = (Un_n * 10.0.pow(3.0)) / (sqrt(3.0) * ZallNMinFormatted)
        val Il_n_2_min = Il_n_3_min * (sqrt(3.0) / 2.0)
        val IlN3Formatted = String.format("%.3f", Il_n_3).toDouble()
        val IlN2Formatted = String.format("%.3f", Il_n_2).toDouble()
        val IlN3MinFormatted = String.format("%.3f", Il_n_3_min).toDouble()
        val IlN2MinFormatted = String.format("%.3f", Il_n_2_min).toDouble()

        return buildString {
            appendLine("═══════════════════════════════════")
            appendLine("РЕЗУЛЬТАТИ РОЗРАХУНКУ ЗАВДАННЯ 3")
            appendLine("═══════════════════════════════════")
            appendLine()
            appendLine("1. Реактивний опір трансформатора:")
            appendLine("   Xt = (Uk_max × Uv_n²) / (100 × Snom_t) = ${"%.3f".format(XtFormatted)} Ом")
            appendLine()
            appendLine("2. Опори на шинах 10 кВ (приведені до 110 кВ):")
            appendLine("   Rsh = Rc_n = ${"%.3f".format(RshFormatted)} Ом")
            appendLine("   Xsh = Xc_n + Xt = ${"%.3f".format(XshFormatted)} Ом")
            appendLine("   Zsh = √(Rsh² + Xsh²) = ${"%.3f".format(ZshFormatted)} Ом")
            appendLine("   Rsh_min = Rc_min = ${"%.3f".format(RshMinFormatted)} Ом")
            appendLine("   Xsh_min = Xc_min + Xt = ${"%.3f".format(XshMinFormatted)} Ом")
            appendLine("   Zsh_min = √(Rsh_min² + Xsh_min²) = ${"%.3f".format(ZshMinFormatted)} Ом")
            appendLine()
            appendLine("3. Струми КЗ на шинах 10 кВ (приведені до 110 кВ):")
            appendLine("   Ish_3 = (Uv_n × 10³) / (√3 × Zsh) = ${"%.3f".format(Ish3Formatted)} А")
            appendLine("   Ish_2 = Ish_3 × (√3 / 2) = ${"%.3f".format(Ish2Formatted)} А")
            appendLine("   Ish_3_min = (Uv_n × 10³) / (√3 × Zsh_min) = ${"%.3f".format(Ish3MinFormatted)} А")
            appendLine("   Ish_2_min = Ish_3_min × (√3 / 2) = ${"%.3f".format(Ish2MinFormatted)} А")
            appendLine()
            appendLine("4. Коефіцієнт приведення:")
            appendLine("   Kpr = Un_n² / Uv_n² = ${"%.3f".format(KprFormatted)}")
            appendLine()
            appendLine("5. Опори на шинах 10 кВ:")
            appendLine("   Rsh_n = Rsh × Kpr = ${"%.3f".format(RshNFormatted)} Ом")
            appendLine("   Xsh_n = Xsh × Kpr = ${"%.3f".format(XshNFormatted)} Ом")
            appendLine("   Zsh_n = √(Rsh_n² + Xsh_n²) = ${"%.3f".format(ZshNFormatted)} Ом")
            appendLine("   Rsh_n_min = Rsh_min × Kpr = ${"%.3f".format(RshNMinFormatted)} Ом")
            appendLine("   Xsh_n_min = Xsh_min × Kpr = ${"%.3f".format(XshNMinFormatted)} Ом")
            appendLine("   Zsh_n_min = √(Rsh_n_min² + Xsh_n_min²) = ${"%.3f".format(ZshNMinFormatted)} Ом")
            appendLine()
            appendLine("6. Дійсні струми КЗ на шинах 10 кВ:")
            appendLine("   Ish_n_3 = (Un_n × 10³) / (√3 × Zsh_n) = ${"%.3f".format(IshN3Formatted)} А")
            appendLine("   Ish_n_2 = Ish_n_3 × (√3 / 2) = ${"%.3f".format(IshN2Formatted)} А")
            appendLine("   Ish_n_3_min = (Un_n × 10³) / (√3 × Zsh_n_min) = ${"%.3f".format(IshN3MinFormatted)} А")
            appendLine("   Ish_n_2_min = Ish_n_3_min × (√3 / 2) = ${"%.3f".format(IshN2MinFormatted)} А")
            appendLine()
            appendLine("7. Опори лінії:")
            appendLine("   l = ${"%.2f".format(l)} км")
            appendLine("   Rl = l × Ro = ${"%.3f".format(RlFormatted)} Ом")
            appendLine("   Xl = l × Xo = ${"%.3f".format(XlFormatted)} Ом")
            appendLine()
            appendLine("8. Опори в точці 10:")
            appendLine("   Rall_n = Rl + Rsh_n = ${"%.3f".format(RallNFormatted)} Ом")
            appendLine("   Xall_n = Xl + Xsh_n = ${"%.3f".format(XallNFormatted)} Ом")
            appendLine("   Zall_n = √(Rall_n² + Xall_n²) = ${"%.3f".format(ZallNFormatted)} Ом")
            appendLine("   Rall_n_min = Rl + Rsh_n_min = ${"%.3f".format(RallNMinFormatted)} Ом")
            appendLine("   Xall_n_min = Xl + Xsh_n_min = ${"%.3f".format(XallNMinFormatted)} Ом")
            appendLine("   Zall_n_min = √(Rall_n_min² + Xall_n_min²) = ${"%.3f".format(ZallNMinFormatted)} Ом")
            appendLine()
            appendLine("9. Струми КЗ в точці 10:")
            appendLine("   Il_n_3 = (Un_n × 10³) / (√3 × Zall_n) = ${"%.3f".format(IlN3Formatted)} А")
            appendLine("   Il_n_2 = Il_n_3 × (√3 / 2) = ${"%.3f".format(IlN2Formatted)} А")
            appendLine("   Il_n_3_min = (Un_n × 10³) / (√3 × Zall_n_min) = ${"%.3f".format(IlN3MinFormatted)} А")
            appendLine("   Il_n_2_min = Il_n_3_min × (√3 / 2) = ${"%.3f".format(IlN2MinFormatted)} А")
            appendLine("═══════════════════════════════════")
        }
    } catch (e: Exception) {
        return "Помилка: перевірте введені дані!"
    }
}

