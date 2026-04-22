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
fun ResumenregresoScreen() {
    val verdeFondo = Color(0xFF2D461E)
    val cremaTarjeta = Color(0xFFE8D596)
    val cafeTexto = Color(0xFF2D2D2D)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 30.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    // SE AÑADIÓ ESTA ROTACIÓN PARA QUE APUNTE A LA IZQUIERDA
                    modifier = Modifier.size(24.dp).rotate(180f),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Resumen de Regreso",
                color = Color.White,
                fontSize = 30.sp, // Es un tamaño grande, ideal para títulos
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth() // Aseguramos que tenga espacio para centrarse
                    .padding(vertical = 13.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- TARJETA DE RESUMEN DE REGRESO ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(cremaTarjeta)
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_car_check),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp),
                    tint = cafeTexto
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Detalles de tu Vuelta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = cafeTexto
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = cafeTexto.copy(alpha = 0.2f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Se asume que ResumenRowCompact está en Resumen.kt
            ResumenRowCompact(R.drawable.ic_location, "Origen: Acacías")
            ResumenRowCompact(R.drawable.ic_map_route, "Destino: Villavicencio")
            ResumenRowCompact(R.drawable.ic_calendarioa, "Fecha de Regreso:")
            ResumenRowCompact(R.drawable.ic_clock, "Hora de Regreso:")
            ResumenRowCompact(R.drawable.ic_seat_choice, "Silla de Regreso:")
            ResumenRowCompact(R.drawable.ic_price_tag, "Total a Pagar:")
        }

        // --- DUSTER (Espacio flexible) ---
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.duster),
                contentDescription = null,
                modifier = Modifier.size(600.dp),
                contentScale = ContentScale.Fit
            )
        }

        // --- ÚNICO BOTÓN: CONFIRMAR RESERVA DE MI VIAJE ---
        Button(
            onClick = { /* Acción final de reserva */ },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaTarjeta),
            shape = RoundedCornerShape(15.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(
                text = "CONFIRMAR RESERVA DE MI VIAJE",
                color = cafeTexto,
                fontSize = 16.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.fillMaxWidth(), // Para que ocupe todo el ancho
                textAlign = androidx.compose.ui.text.style.TextAlign.Center // Centra el contenido
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun ResumenregresoPreview() {
    ResumenregresoScreen()
}