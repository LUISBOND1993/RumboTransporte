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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DiaregresoScreen(ciudadDestino: String, onBackClick: () -> Unit, onDiaConfirmado: (Int) -> Unit) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick, modifier = Modifier.background(Color.White.copy(0.8f), CircleShape)) {
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
                text = "COMPRA TU PASAJE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // Usando los componentes con prefijo local para evitar errores
            DiaRegFilaOpcion("SOLO IDA", seleccionado = false, verdeFondoCalendario)
            DiaRegFilaOpcion("IDA Y REGRESO", seleccionado = true, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(15.dp))

            DiaRegCajaDireccion("DIRECCIÓN DE ORIGEN", "VILLAVICENCIO", verdeFondoCalendario)
            Spacer(modifier = Modifier.height(10.dp))
            DiaRegCajaDireccion("INGRESA TU DESTINO:", ciudadDestino, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(20.dp))

            Text("FECHA DE REGRESO", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)

            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                DiaRegBotonMes("Enero", false, verdeBotonesMes)
                DiaRegBotonMes("Febrero", false, verdeBotonesMes)
                DiaRegBotonMes("Marzo", true, Color.White)
            }

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

                    for (fila in 0 until 5) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            for (col in 0 until 7) {
                                val dia = fila * 7 + col + 1
                                if (dia <= 31) {
                                    DiaRegItemDia(dia, cremaCirculo, verdeFondoCalendario) {
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

// ── COMPONENTES LOCALES PARA Diaregreso.kt ──

@Composable
fun DiaRegFilaOpcion(texto: String, seleccionado: Boolean, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
    ) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(if (seleccionado) color else Color.Gray.copy(0.4f)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
    }
}

@Composable
fun DiaRegCajaDireccion(label: String, ciudad: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Surface(
            modifier = Modifier.fillMaxWidth().height(40.dp),
            color = color,
            shape = RoundedCornerShape(22.dp)
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(ciudad, color = Color.White, modifier = Modifier.padding(start = 16.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DiaRegBotonMes(texto: String, activo: Boolean, colorFondo: Color) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = if (activo) colorFondo else colorFondo.copy(alpha = 0.4f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            color = if (activo && texto == "Marzo") Color.Black else Color.White,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun DiaRegItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(35.dp).clip(CircleShape).background(colorCirculo).clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = dia.toString(), color = colorTexto, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DiaregresoPreview() {
    DiaregresoScreen(ciudadDestino = "ACACÍAS", onBackClick = {}, onDiaConfirmado = {})
}