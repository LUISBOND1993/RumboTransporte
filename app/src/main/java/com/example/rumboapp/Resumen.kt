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
fun ResumenScreen() {
    val verdeFondo = Color(0xFF2D461E)
    val cremaTarjeta = Color(0xFFE8D596)
    val cafeTexto = Color(0xFF2D2D2D)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 20.dp, vertical = 25.dp), // Padding vertical reducido
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp) // Reducido de 40 a 35
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { /* Volver */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp)) // Espacio reducido

        // --- TARJETA DE RESUMEN (Más compacta) ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(cremaTarjeta)
                .padding(18.dp) // Padding interno reducido de 25 a 18
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_car_check),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp), // Reducido de 45 a 35
                    tint = cafeTexto
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Información del Viaje",
                    fontSize = 20.sp, // Reducido de 24 a 20
                    fontWeight = FontWeight.Bold,
                    color = cafeTexto
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = cafeTexto.copy(alpha = 0.2f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Filas con padding vertical menor (6.dp en lugar de 8.dp)
            ResumenRowCompact(R.drawable.ic_location, "Origen:")
            ResumenRowCompact(R.drawable.ic_map_route, "Destino:")
            ResumenRowCompact(R.drawable.ic_calendarioa, "Fecha de Viaje :")
            ResumenRowCompact(R.drawable.ic_clock, "Hora de Viaje:")
            ResumenRowCompact(R.drawable.ic_seat_choice, "Silla:")
            ResumenRowCompact(R.drawable.ic_price_tag, "Precio:")
        }

        // --- DUSTER (Tamaño medio para dejar espacio al botón) ---
        Box(
            modifier = Modifier
                .weight(1f) // Usa el espacio disponible dinámicamente
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Box(modifier = Modifier.size(350.dp)) { // Tamaño controlado
                Image(
                    painter = painterResource(id = R.drawable.duster),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

            }
        }

        // --- BOTÓN IR AL PAGO (Ahora siempre visible) ---
        Button(
            onClick = { /* Navegar */ },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaTarjeta),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Ir al Pago",
                color = cafeTexto,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun ResumenRowCompact(iconId: Int, label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp), // Padding reducido para ahorrar espacio
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = null,
            modifier = Modifier.size(20.dp), // Iconos un poco más pequeños
            tint = Color(0xFF2D2D2D)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            fontSize = 16.sp, // Fuente ligeramente menor
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2D2D2D)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ResumenPreview() {
    ResumenScreen()
}