package com.example.lab_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TasksScreen()
            }
        }
    }
}

@Composable
fun TasksScreen() {
    // додаємо скрол
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()), // <-- прокрутка
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Text("Завдання 1", style = MaterialTheme.typography.headlineMedium)
        FuelCalculatorTask1()

        Divider()

        Text("Завдання 2", style = MaterialTheme.typography.headlineMedium)
        FuelCalculatorTask2()
    }
}

@Composable
fun FuelCalculatorTask1() {
    var hp by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    var sp by remember { mutableStateOf("") }
    var np by remember { mutableStateOf("") }
    var op by remember { mutableStateOf("") }
    var ap by remember { mutableStateOf("") }
    var w by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value = hp,
            onValueChange = { hp = it },
            label = buildLabel("Hp"), // автоматично "Hᵖ"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = c,
            onValueChange = { c = it },
            label = buildLabel("C"), // просто "C"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sp,
            onValueChange = { sp = it },
            label = buildLabel("Sp"), // "Sᵖ"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = np,
            onValueChange = { np = it },
            label = buildLabel("Np"), // "Nᵖ"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = op,
            onValueChange = { op = it },
            label = buildLabel("Op"), // "Oᵖ"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = w,
            onValueChange = { w = it },
            label = buildLabel("W"), // "W"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = ap,
            onValueChange = { ap = it },
            label = buildLabel("Ap"), // "Aᵖ"
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = {
            result = calculateTask1(hp, c, sp, np, op, ap, w)
        }) {
            Text("Обрахувати")
        }

        Text(result)
    }
}
fun calculateTask1(
    hp: String,
    c: String,
    sp: String,
    np: String,
    op: String,
    ap: String,
    w: String
): String {
    val hVal = hp.toDoubleOrNull() ?: 0.0
    val cVal = c.toDoubleOrNull() ?: 0.0
    val sVal = sp.toDoubleOrNull() ?: 0.0
    val nVal = np.toDoubleOrNull() ?: 0.0
    val oVal = op.toDoubleOrNull() ?: 0.0
    val aVal = ap.toDoubleOrNull() ?: 0.0
    val wVal = w.toDoubleOrNull() ?: 0.0



    val kpc = 100/(100 - wVal)
    val krg = 100/(100 - wVal-aVal)

    val qpn = (339*cVal) + (1030*hVal) - (108.8*(oVal-sVal)) - (25*wVal)
    val qcn = ((qpn/1000) + 0.025*wVal)*(100/(100-wVal))
    val qgn = ((qpn/1000) + 0.025*wVal)*(100/(100-wVal-aVal))
    return """
        1.1 Коефіцієнт переходу від робочої до сухої маси 
        Кпс = ${"%.2f".format(kpc)}
        
        1.2 Коефіцієнт переходу від робочої до горючої маси 
        Кпг = ${"%.2f".format(krg)}
        
        1.3 Склад сухої маси палива
        Hc = ${"%.2f".format((hVal*kpc))} %
        Cc = ${"%.2f".format((cVal*kpc))} %
        Sc = ${"%.2f".format((sVal*kpc))} %
        Nc = ${"%.2f".format((nVal*kpc))} %
        Oc = ${"%.2f".format((oVal*kpc))} %
        Ac = ${"%.2f".format((aVal*kpc))} %
        
        1.4 Склад горючої маси палива
        Hг = ${"%.2f".format((hVal*krg))} %
        Cг = ${"%.2f".format((cVal*krg))} %
        Sг = ${"%.2f".format((sVal*krg))} %
        Nг = ${"%.2f".format((nVal*krg))} %
        Oг = ${"%.2f".format((oVal*krg))} %
        
        1.5 Нижча теплота згорання для робочої маси 
        QpН = ${"%.2f".format((qpn))} [кДж/кг] = ${"%.2f".format((qpn/1000))} [МДж/кг]
        
        1.6 Нижча теплота згоряння для сухої маси
        ${"%.2f".format((qcn))} [МДж/кг]
        
        1.7 Нижча теплота згоряння для горючої маси
        ${"%.2f".format((qgn))} [МДж/кг]
    """.trimIndent()
}
@Composable
fun FuelCalculatorTask2() {
    var hg by remember { mutableStateOf("") }
    var cg by remember { mutableStateOf("") }
    var sg by remember { mutableStateOf("") }
    var og by remember { mutableStateOf("") }
    var vg by remember { mutableStateOf("") }
    var ag by remember { mutableStateOf("") }
    var wg by remember { mutableStateOf("") }
    var Qdaf by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        OutlinedTextField(
            value = hg,
            onValueChange = { hg = it },
            label = buildLabel("Hг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cg,
            onValueChange = { cg = it },
            label = buildLabel("Cг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sg,
            onValueChange = { sg = it },
            label = buildLabel("Sг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = og,
            onValueChange = { og = it },
            label = buildLabel("Oг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = vg,
            onValueChange = { vg = it },
            label = buildLabel("Vг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = wg,
            onValueChange = { wg = it },
            label = buildLabel("Wг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = ag,
            onValueChange = { ag = it },
            label = buildLabel("Aг"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = Qdaf,
            onValueChange = { Qdaf = it },
            label = buildLabel("Q"),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            result = calculateTask2(hg, cg, sg, og, vg, wg, ag, Qdaf)
        }) {
            Text("Обрахувати")
        }

        Text(result)
    }
}
fun calculateTask2(
    hg: String,
    cg: String,
    sg: String,
    og: String,
    vg: String,
    wg: String,
    ag: String,
    Qdaf: String
): String {
    val hVal = hg.toDoubleOrNull() ?: 0.0
    val cVal = cg.toDoubleOrNull() ?: 0.0
    val sVal = sg.toDoubleOrNull() ?: 0.0
    val oVal = og.toDoubleOrNull() ?: 0.0
    val vVal = vg.toDoubleOrNull() ?: 0.0
    val wVal = wg.toDoubleOrNull() ?: 0.0
    val aVal = ag.toDoubleOrNull() ?: 0.0
    val qVal = Qdaf.toDoubleOrNull() ?: 0.0

    // коефіцієнти
    val Qri = qVal*((100-wVal-aVal)/100) - (0.025*wVal)

    return """
        2.1 Склад робочої маси мазуту становитеме
        H = ${"%.2f".format((hVal*((100-wVal-aVal)/100)))} %
        C = ${"%.2f".format((cVal*((100-wVal-aVal)/100)))} %
        S = ${"%.2f".format((sVal*((100-wVal-aVal)/100)))} %
        O = ${"%.2f".format((oVal*((100-wVal-aVal)/100)))} %
        V = ${"%.2f".format((vVal*((100-wVal)/100)))} мг/кг
        A = ${"%.2f".format((aVal*((100-wVal)/100)))} %
        
        2.2 Нижча теплота згорання мазуту
        ${"%.2f".format(Qri)} МДж/кг

        """.trimIndent()
}

@Composable
fun buildLabel(variableName: String): @Composable () -> Unit {
    return {
        Text(
            buildAnnotatedString {
                if (variableName.length == 1) {
                    // якщо тільки 1 літера
                    append(variableName.uppercase())
                } else {
                    // першу робимо великою
                    append(variableName[0].uppercase())

                    // решту (наприклад, "p") робимо степенем
                    withStyle(
                        style = SpanStyle(
                            fontSize = 12.sp,
                            baselineShift = BaselineShift.Superscript
                        )
                    ) {
                        append(variableName.substring(1))
                    }
                }
                if(variableName[0] == 'V'){
                    append(" (мг/кг)")
                } else if (variableName[0] == 'A' && variableName[1] != 'p'){
                    append( " ")
                } else if (variableName[0] == 'Q'){
                    append(" (МДж/кг)")
                } else{
                    append(" (%)")
                }

            }
        )
    }
}



