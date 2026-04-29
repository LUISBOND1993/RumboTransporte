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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CalendarioregresoScreen(
    onDiaSeleccionado: (Int, String) -> Unit,
    onIrADestino: () -> Unit,
    ciudadDestino: String,
    direccionOrigen: String,
    onOrigenCambiado: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val cremaCirculo = Color(0xFFE8D596)

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
                text = "TU VIAJE DE REGRESO",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 15.dp)
            )

            // ENTRADAS DE DATOS
            RegCalEntradaDireccion("DIRECCIÓN DE ORIGEN", direccionOrigen, verdeFondoCalendario, onOrigenCambiado)

            Spacer(modifier = Modifier.height(8.dp))

            RegCalBotonDestino("CIUDAD DE REGRESO:", ciudadDestino, verdeFondoCalendario, onIrADestino)

            Text(
                "FECHA DE REGRESO",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 15.dp)
            )

            // SELECTOR DE MESES
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
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

            // CONTENEDOR DEL CALENDARIO
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = mesSeleccionado.uppercase(),
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold
                    )

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // GENERACIÓN DE DÍAS
                    for (fila in 0 until 5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (col in 0 until 7) {
                                val dia = fila * 7 + col + 1
                                if (dia <= 31) {
                                    RegCalItemDia(dia, cremaCirculo, verdeFondoCalendario, onClick = {
                                        onDiaSeleccionado(dia, mesSeleccionado)
                                    })
                                } else {
                                    Spacer(modifier = Modifier.size(34.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --- COMPONENTES ÚNICOS PARA ESTA PANTALLA ---

@Composable
fun RegCalEntradaDireccion(label: String, valor: String, colorFondo: Color, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        TextField(
            value = valor,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(22.dp)),
            placeholder = { Text("Ingresa tu dirección de recogida", color = Color.White.copy(0.6f), fontSize = 14.sp) },
            textStyle = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorFondo,
                unfocusedContainerColor = colorFondo,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )
    }
}

@Composable
fun RegCalBotonDestino(label: String, ciudad: String, colorFondo: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(colorFondo)
                .clickable { onClick() }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = ciudad.ifEmpty { "Selecciona tu ciudad" },
                color = if (ciudad.isEmpty()) Color.White.copy(0.6f) else Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RegCalItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit = {}) {
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
fun CalendarioregresoPreview() {
    CalendarioregresoScreen(
        onDiaSeleccionado = { _, _ -> },
        onIrADestino = {},
        ciudadDestino = "", // Vació para mostrar "Selecciona tu ciudad"
        direccionOrigen = "", // Vacío para mostrar "Ingresa tu dirección de recogida"
        onOrigenCambiado = {},
        onBackClick = {}
    )
}