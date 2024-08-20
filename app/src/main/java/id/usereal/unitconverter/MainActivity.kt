package id.usereal.unitconverter

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.usereal.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UnitConverter() {
    // Untuk Input
    var inputTextMasukan by remember { mutableStateOf("") }
    // Untuk Hasil
    var resultString by remember { mutableStateOf("") }
    // Untuk pemilihan Input Ukuran
    var iExpended by remember { mutableStateOf(false) }
    // Untuk pemilihan Output Ukuran
    var oExpended by remember { mutableStateOf(false) }
    // Untuk mengaktifkan dropdown untuk ukuran Input dimulai dari CM
    var valueDropdownInput by remember { mutableStateOf("CM") }
    // Untuk mengaktifkan dropdown untuk ukuran Output dimulai dari M
    var valueDropdownOutput by remember { mutableStateOf("M") }
    // textStyle
    val customTextStyle = TextStyle(
        color = Color.Blue,
        fontSize = 32.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold

    )
    // Fungsi konversi
    fun getConversionFactor(inputUnit: String, outputUnit: String): Double {
        val conversionMap = mapOf(
            "MM" to 0.001,
            "CM" to 0.01,
            "M" to 1.0
        )
        val inputFactor = conversionMap[inputUnit] ?: 1.0
        val outputFactor = conversionMap[outputUnit] ?: 1.0
        return inputFactor / outputFactor
    }

    // Fungsi untuk konversi nilai
    fun convertValue(): String {
        val inputValueDouble = inputTextMasukan.toDoubleOrNull() ?: 0.0
        val result = inputValueDouble * getConversionFactor(valueDropdownInput, valueDropdownOutput)
        return result.toString()

    }

    // Panggil fungsi konversi ketika ada perubahan pada input atau dropdown
    LaunchedEffect(inputTextMasukan, valueDropdownInput, valueDropdownOutput) {
        resultString = convertValue()
    }
11
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", style = customTextStyle)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier.width(250.dp),
            value = inputTextMasukan,
            onValueChange = {
                inputTextMasukan = it
            },
            label = {
                Text(text = "Masukan nilai")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box {
                Button(onClick = { iExpended = !iExpended }) {
                    Text(text = valueDropdownInput)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop Down Input")
                }
                DropdownMenu(expanded = iExpended, onDismissRequest = { iExpended = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "MM") },
                        onClick = {
                            iExpended = false
                            valueDropdownInput = "MM"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "CM") },
                        onClick = {
                            iExpended = false
                            valueDropdownInput = "CM"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "M") },
                        onClick = {
                            iExpended = false
                            valueDropdownInput = "M"
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpended = !oExpended }) {
                    Text(text = valueDropdownOutput)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop Down Output")
                }
                DropdownMenu(expanded = oExpended, onDismissRequest = { oExpended = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "MM") },
                        onClick = {
                            oExpended = false
                            valueDropdownOutput = "MM"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "CM") },
                        onClick = {
                            oExpended = false
                            valueDropdownOutput = "CM"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "M") },
                        onClick = {
                            oExpended = false
                            valueDropdownOutput = "M"
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Result: $resultString $valueDropdownOutput", modifier = Modifier.width(250.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineMedium)
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}