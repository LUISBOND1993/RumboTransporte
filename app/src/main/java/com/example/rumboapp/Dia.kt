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
fun DiaScreen(onDiaConfirmado: (Int) -> Unit) {
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
                        .clickable { /* Atrás */ },
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

            Spacer(modifier = Modifier.height(15.dp))

            FilaOpcionDia("SOLO IDA", seleccionado = true, verdeFondoCalendario)
            Spacer(modifier = Modifier.height(8.dp))
            FilaOpcionDia("IDA Y REGRESO", seleccionado = false, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(15.dp))

            // --- CAJAS VACÍAS (Para que se llenen dinámicamente luego) ---
            CajaDireccionDia("DIRECCIÓN DE ORIGEN", "", verdeFondoCalendario)
            Spacer(modifier = Modifier.height(10.dp))
            CajaDireccionDia("INGRESA TU DESTINO:", "", verdeFondoCalendario)

            Spacer(modifier = Modifier.height(20.dp))

            Text("FECHA DE IDA", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)

            Row(modifier = Modifier.padding(vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
                BotonMesDia("Enero", false, verdeBotonesMes)
                BotonMesDia("Febrero", false, verdeBotonesMes)
                BotonMesDia("Marzo", true, Color.White, Color.Black)
                Spacer(modifier = Modifier.width(8.dp))
                Text(">", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            // --- CALENDARIO ---
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

                    Spacer(modifier = Modifier.height(10.dp))

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
                                        ItemDiaCalendario(dia, cremaCirculo, verdeFondoCalendario) {
                                            onDiaConfirmado(dia)
                                        }
                                    } else {
                                        Spacer(modifier = Modifier.size(35.dp))
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
}

@Composable
fun ItemDiaCalendario(numero: Int, colorCirculo: Color, colorTexto: Color, alHacerClic: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            // Aquí quité la marca fija del día 13 para que todos nazcan igual
            .background(colorCirculo)
            .clickable { alHacerClic() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = numero.toString(),
            color = colorTexto,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun BotonMesDia(texto: String, activo: Boolean, colorFondo: Color, colorTexto: Color = Color.White) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = colorFondo,
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            color = colorTexto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FilaOpcionDia(texto: String, seleccionado: Boolean, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(if (seleccionado) color else Color.Gray.copy(0.4f))
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
    }
}

@Composable
fun CajaDireccionDia(label: String, contenido: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color),
            contentAlignment = Alignment.CenterStart
        ) {
            // Se pintará solo si le pasamos algo en el futuro
            if (contenido.isNotEmpty()) {
                Text(
                    contenido,
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiaPreview() {
    DiaScreen(onDiaConfirmado = {})
}