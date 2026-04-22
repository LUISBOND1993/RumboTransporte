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

@Composable
fun DiaScreen(ciudadDestino: String, onBackClick: () -> Unit, onDiaConfirmado: (Int) -> Unit) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val cremaCirculo = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_paisaje),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabecera con botón atrás y título
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                text = "COMPRA TU PASAJE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 20.dp)
            )

            // LLAMADA A CAJA DIRECCIÓN (Solución al error 1)
            CajaDireccionDia("INGRESA TU DESTINO:", ciudadDestino, verdeFondoCalendario)

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(alpha = 0.95f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "MARZO 2026",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp).align(Alignment.CenterHorizontally)
                    )

                    // Generación de días
                    for (fila in 0 until 5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (col in 0 until 7) {
                                val diaActual = fila * 7 + col + 1
                                if (diaActual <= 31) {
                                    // LLAMADA A ITEM DIA (Solución al error 2)
                                    ItemDia(
                                        dia = diaActual,
                                        colorCirculo = if(diaActual == 20) cremaCirculo else Color.Transparent,
                                        colorTexto = if(diaActual == 20) verdeFondoCalendario else Color.White
                                    ) { onDiaConfirmado(diaActual) }
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

// --- FUNCIONES QUE FALTABAN ---

@Composable
fun CajaDireccionDia(label: String, contenido: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(color),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = contenido,
                color = Color.White,
                modifier = Modifier.padding(start = 15.dp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ItemDia(dia: Int, colorCirculo: Color, colorTexto: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(35.dp)
            .clip(CircleShape)
            .background(colorCirculo)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.toString(),
            color = colorTexto,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}