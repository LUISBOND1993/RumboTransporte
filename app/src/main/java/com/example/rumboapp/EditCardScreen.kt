package com.example.rumboapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerdeFondo = Color(0xFF1B3011)
private val VerdeContenedor = Color(0xFF2D4B1E)
private val ColorDorado = Color(0xFFD4AF37)
private val ColorCremita = Color(0xFFE2E2BD)

@Composable
fun EditCardScreen(
    numeroInicial: String = "",
    nombreInicial: String = "",
    fechaInicial: String = "",
    cvvInicial: String = "",
    onBackClick: () -> Unit,
    onGuardarClick: (String, String, String, String) -> Unit
) {
    var numero by remember { mutableStateOf(numeroInicial) }
    var nombre by remember { mutableStateOf(nombreInicial) }
    var fecha by remember { mutableStateOf(fechaInicial) }
    var cvv by remember { mutableStateOf(cvvInicial) }

    fun formatearNumero(raw: String): String {
        val solo = raw.filter { it.isDigit() }.take(16)
        return solo.chunked(4).joinToString(" ")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VerdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        // Encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, end = 16.dp, bottom = 8.dp)
        ) {
            Surface(
                modifier = Modifier.size(36.dp).align(Alignment.CenterStart),
                color = Color.White.copy(alpha = 0.85f),
                shape = CircleShape
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_revert),
                        contentDescription = "Atrás",
                        tint = Color.Black
                    )
                }
            }
            Text(
                text = if (numeroInicial.isEmpty()) "Agregar Tarjeta" else "Editar Tarjeta",
                color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Vista previa
        Surface(
            modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 24.dp),
            color = VerdeContenedor, shape = RoundedCornerShape(16.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                Text(
                    text = if (numero.isBlank()) "•••• •••• •••• ••••" else formatearNumero(numero),
                    color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                Row(modifier = Modifier.align(Alignment.BottomStart)) {
                    Column {
                        Text("TITULAR", color = ColorCremita, fontSize = 10.sp)
                        Text(
                            nombre.uppercase().ifBlank { "NOMBRE APELLIDO" },
                            color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column(horizontalAlignment = Alignment.End) {
                        Text("VENCE", color = ColorCremita, fontSize = 10.sp)
                        Text(
                            fecha.ifBlank { "MM/AA" },
                            color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            CardField(label = "Número de la tarjeta:", valor = formatearNumero(numero), onValueChange = {
                val solo = it.filter { c -> c.isDigit() }
                if (solo.length <= 16) numero = solo
            }, keyboardType = KeyboardType.Number, placeholder = "0000 0000 0000 0000")

            Spacer(modifier = Modifier.height(16.dp))

            CardField(label = "Nombre del titular:", valor = nombre, onValueChange = { nombre = it }, placeholder = "Ej: JUAN PEREZ")

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    CardField(label = "Vencimiento:", valor = fecha, onValueChange = {
                        val solo = it.filter { c -> c.isDigit() }.take(4)
                        fecha = when {
                            solo.length > 2 -> "${solo.take(2)}/${solo.drop(2)}"
                            else -> solo
                        }
                    }, keyboardType = KeyboardType.Number, placeholder = "MM/AA")
                }
                Column(modifier = Modifier.weight(1f)) {
                    CardField(label = "CVV:", valor = cvv, onValueChange = { if (it.length <= 3) cvv = it }, keyboardType = KeyboardType.Number, placeholder = "000")
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { onGuardarClick(numero, nombre, fecha, cvv) },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text("Guardar Tarjeta", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun CardField(
    label: String,
    valor: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    placeholder: String = ""
) {
    Column {
        Text(label, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 6.dp))
        TextField(
            value = valor,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ColorCremita,
                unfocusedContainerColor = ColorCremita,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
    }
}
