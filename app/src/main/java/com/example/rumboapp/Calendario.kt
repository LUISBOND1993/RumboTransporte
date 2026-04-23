package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CalendarioScreen(
    onDiaSeleccionado: (Int) -> Unit,
    onIrADestino: () -> Unit,
    ciudadDestino: String,
    direccionOrigen: String,
    onOrigenCambiado: (String) -> Unit
) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val verdeBotonesMes = Color(0xFF4C6636)
    val cremaCirculo = Color(0xFFE8D596)

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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(
                    onClick = { /* Puedes agregar navegación atrás si gustas */ },
                    modifier = Modifier.background(Color.White.copy(0.8f), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.rotate(180f)
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp).clip(CircleShape)
                )
            }

            Text("COMPRA TU PASAJE", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 20.dp))

            CalFilaOpcion("SOLO IDA", true, verdeFondoCalendario)
            CalFilaOpcion("IDA Y REGRESO", false, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(15.dp))

            CalEntradaDireccion("DIRECCIÓN DE ORIGEN", direccionOrigen, verdeFondoCalendario, onOrigenCambiado)
            CalBotonDestino("INGRESA TU DESTINO:", ciudadDestino, verdeFondoCalendario, onIrADestino)

            Text("FECHA DE IDA", fontSize = 23.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp))

            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                CalBotonMes("Enero", false, verdeBotonesMes)
                CalBotonMes("Febrero", false, verdeBotonesMes)
                CalBotonMes("Marzo", true, verdeBotonesMes)
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    for (fila in 0 until 5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (col in 0 until 7) {
                                val dia = fila * 7 + col + 1
                                if (dia <= 31) {
                                    CalItemDia(dia, cremaCirculo, verdeFondoCalendario, onClick = { onDiaSeleccionado(dia) })
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalEntradaDireccion(label: String, valor: String, colorFondo: Color, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        TextField(
            value = valor,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(22.dp)),
            placeholder = { Text("Escribe aquí...", color = Color.White.copy(0.6f), fontSize = 14.sp) },
            textStyle = TextStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorFondo,
                unfocusedContainerColor = colorFondo,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White
            )
        )
    }
}

@Composable
fun CalBotonDestino(label: String, ciudad: String, colorFondo: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp).clip(RoundedCornerShape(22.dp)).background(colorFondo).clickable { onClick() }.padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (ciudad.isEmpty()) "Seleccionar destino..." else ciudad,
                color = if (ciudad.isEmpty()) Color.White.copy(0.6f) else Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CalFilaOpcion(texto: String, seleccionado: Boolean, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(if (seleccionado) color else Color.Gray.copy(0.4f)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun CalBotonMes(texto: String, activo: Boolean, color: Color) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = if (activo) color else color.copy(alpha = 0.4f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = texto, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CalItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.size(34.dp).clip(CircleShape).background(colorCirculo).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = dia.toString(), color = colorTexto, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold)
    }
}