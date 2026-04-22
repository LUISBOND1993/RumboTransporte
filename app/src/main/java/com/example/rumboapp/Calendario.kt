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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CalendarioScreen(
    onDiaSeleccionado: (Int) -> Unit,
    onIrADestino: () -> Unit
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
                    onClick = { /* Acción de volver */ },
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

            // Usamos los nombres renombrados para evitar el conflicto
            CalFilaOpcion("SOLO IDA", true, verdeFondoCalendario, onClick = {})
            CalFilaOpcion("IDA Y REGRESO", false, verdeFondoCalendario, onClick = {})

            Spacer(modifier = Modifier.height(15.dp))

            CalCajaDireccion("DIRECCIÓN DE ORIGEN", verdeFondoCalendario, onIrADestino)
            CalCajaDireccion("INGRESA TU DESTINO:", verdeFondoCalendario, onIrADestino)

            Text("FECHA DE IDA", fontSize = 23.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp))

            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                CalBotonMes("Enero", false, verdeBotonesMes, onClick = {})
                CalBotonMes("Febrero", false, verdeBotonesMes, onClick = {})
                CalBotonMes("Marzo", true, verdeBotonesMes, onClick = {})
            }

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
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
                                    Spacer(modifier = Modifier.size(35.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// ── COMPONENTES RENOMBRADOS PARA EVITAR CONFLICTOS ──

@Composable
fun CalCajaDireccion(label: String, color: Color, onClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Surface(
            modifier = Modifier.fillMaxWidth().height(35.dp),
            color = color,
            shape = RoundedCornerShape(22.dp),
            onClick = onClick
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text("Seleccionar...", color = Color.White.copy(0.6f), modifier = Modifier.padding(start = 16.dp))
            }
        }
    }
}

@Composable
fun CalFilaOpcion(texto: String, seleccionado: Boolean, color: Color, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp)
    ) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(if (seleccionado) color else Color.Gray.copy(0.4f)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun CalBotonMes(texto: String, activo: Boolean, color: Color, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp).clickable { onClick() },
        color = if (activo) color else color.copy(alpha = 0.4f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(text = texto, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun CalItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.size(35.dp).clip(CircleShape).background(colorCirculo).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = dia.toString(), color = colorTexto, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun CalendarioPreview() {
    CalendarioScreen(onDiaSeleccionado = {}, onIrADestino = {})
}