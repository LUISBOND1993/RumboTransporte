package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DiaregresoScreen(
    ciudadDestino: String,
    direccionOrigen: String,
    onBackClick: () -> Unit,
    onDiaConfirmado: (Int, String) -> Unit
) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val cremaCirculo = Color(0xFFE8D596)

    // Estado para el selector de meses dinámico
    var expanded by remember { mutableStateOf(false) }
    var mesSeleccionado by remember { mutableStateOf("Abril") }
    val meses = listOf(
        "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
        "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_paisaje),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // CABECERA
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.background(Color.White.copy(0.8f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(24.dp).rotate(180f),
                        tint = Color.Unspecified
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(45.dp).clip(CircleShape)
                )
            }

            Text(
                text = "RESUMEN DE TU VIAJE",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 15.dp)
            )

            // RESUMEN DE DATOS SELECCIONADOS
            DiaRegCajaResumen("ORIGEN:", direccionOrigen.ifEmpty { "No especificado" }, verdeFondoCalendario)
            Spacer(modifier = Modifier.height(8.dp))
            DiaRegCajaResumen("DESTINO:", ciudadDestino.ifEmpty { "No seleccionado" }, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "CONFIRMA TU FECHA",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            // SELECTOR DE MESES (DROPDOWN)
            Box(modifier = Modifier.padding(vertical = 10.dp)) {
                Button(
                    onClick = { expanded = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C6636)),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(mesSeleccionado, color = Color.White, fontWeight = FontWeight.Bold)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.White)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(cremaCirculo)
                ) {
                    meses.forEach { mes ->
                        DropdownMenuItem(
                            text = { Text(mes, fontWeight = FontWeight.Bold, color = verdeFondoCalendario) },
                            onClick = {
                                mesSeleccionado = mes
                                expanded = false
                            }
                        )
                    }
                }
            }

            // CALENDARIO
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = mesSeleccionado.uppercase(),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    for (fila in 0 until 5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (col in 0 until 7) {
                                val dia = fila * 7 + col + 1
                                if (dia <= 31) {
                                    DiaRegItemDia(dia, cremaCirculo, verdeFondoCalendario) {
                                        onDiaConfirmado(dia, mesSeleccionado)
                                    }
                                } else {
                                    Spacer(modifier = Modifier.size(34.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DiaRegCajaResumen(label: String, valor: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Surface(
            modifier = Modifier.fillMaxWidth().height(45.dp),
            color = color,
            shape = RoundedCornerShape(22.dp)
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(
                    text = valor,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp
                )
            }
        }
    }
}

@Composable
fun DiaRegItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(CircleShape)
            .background(colorCirculo)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = dia.toString(), color = colorTexto, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiaregresoPreview() {
    DiaregresoScreen(
        ciudadDestino = "Villavicencio",
        direccionOrigen = "Calle 15 #23-45",
        onBackClick = {},
        onDiaConfirmado = { _, _ -> }
    )
}