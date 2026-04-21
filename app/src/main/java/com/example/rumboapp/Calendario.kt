package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CalendarioScreen(onDiaSeleccionado: (Int) -> Unit) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val verdeBotonesMes = Color(0xFF4C6636)
    val cremaCirculo = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo
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
            // Iconos superiores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { /* Acción atrás */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(45.dp).clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "COMPRA TU PASAJE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            FilaOpcion("SOLO IDA", seleccionado = true, verdeFondoCalendario)
            Spacer(modifier = Modifier.height(10.dp))
            FilaOpcion("IDA Y REGRESO", seleccionado = false, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(15.dp))

            CajaDireccion("DIRECCIÓN DE ORIGEN", verdeFondoCalendario)
            Spacer(modifier = Modifier.height(8.dp))
            CajaDireccion("INGRESA TU DESTINO:", verdeFondoCalendario)

            Spacer(modifier = Modifier.height(20.dp))

            Text("FECHA DE IDA", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)

            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                BotonMes("Enero", false, verdeBotonesMes)
                BotonMes("Febrero", false, verdeBotonesMes)
                BotonMes("Marzo", true, verdeBotonesMes)
            }

            // --- CUADRO DEL CALENDARIO ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    val totalDias = 31
                    val columnas = 7
                    val filas = (totalDias + columnas - 1) / columnas

                    Column {
                        for (fila in 0 until filas) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                for (col in 0 until columnas) {
                                    val dia = fila * columnas + col + 1
                                    if (dia <= totalDias) {
                                        ItemDia(dia, cremaCirculo, verdeFondoCalendario) {
                                            onDiaSeleccionado(dia)
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(35.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDia(numero: Int, colorCirculo: Color, colorTexto: Color, alHacerClic: () -> Unit) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(colorCirculo)
            .clickable { alHacerClic() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = numero.toString(),
            color = colorTexto, // Ahora el número es verde oscuro para que se vea dentro del crema
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotonMes(texto: String, activo: Boolean, color: Color) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = if (activo) color else color.copy(alpha = 0.4f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = Color.White,
            fontSize = 13.sp
        )
    }
}

@Composable
fun FilaOpcion(texto: String, seleccionado: Boolean, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(if (seleccionado) color else Color.Gray.copy(0.4f))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
    }
}

@Composable
fun CajaDireccion(label: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(22.dp))
                .background(color)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarioPreview() {
    CalendarioScreen(onDiaSeleccionado = {})
}