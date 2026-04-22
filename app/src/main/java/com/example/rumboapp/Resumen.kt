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
fun ResumenScreen() {
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
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- TARJETA DE RESUMEN CON CÁPSULAS ---
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
                    text = "Información del Viaje",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = cafeTexto
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = cafeTexto.copy(alpha = 0.2f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            // Filas con la nueva estética de cápsula
            ResumenRowCompact(R.drawable.ic_location, "Origen:")
            ResumenRowCompact(R.drawable.ic_map_route, "Destino:")
            ResumenRowCompact(R.drawable.ic_calendarioa, "Fecha:")
            ResumenRowCompact(R.drawable.ic_clock, "Hora:")
            ResumenRowCompact(R.drawable.ic_seat_choice, "Silla:")
            ResumenRowCompact(R.drawable.ic_price_tag, "Precio:")
        }

        // --- DUSTER ---
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

        // --- BOTONES ---
        Button(
            onClick = { /* Ir al flujo de regreso */ },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaTarjeta),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text(
                text = "Apartar viaje de regreso",
                color = cafeTexto,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { /* Ir a pagos */ },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
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
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Etiqueta e Icono
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xFF2D2D2D)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2D2D2D)
            )
        }

        // Cápsula blanca para la información
        Box(
            modifier = Modifier
                .weight(1.2f)
                .height(26.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color.White.copy(alpha = 0.8f))
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "", // Espacio para el dato dinámico
                fontSize = 13.sp,
                color = Color.Black
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ResumenPreview() {
    ResumenScreen()
}