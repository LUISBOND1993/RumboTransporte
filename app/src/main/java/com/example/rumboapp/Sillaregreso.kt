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
fun SillaregresoScreen() {
    val verdeFondo = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

    // Estado para la silla seleccionada en el regreso
    var sillaSeleccionada by remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 20.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- HEADER ---
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
                    // SE AÑADIÓ ESTA ROTACIÓN PARA QUE APUNTE A LA IZQUIERDA
                    modifier = Modifier.size(24.dp).rotate(180f),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "SILLA DE REGRESO", // <-- Cambio de título
                color = Color.White,
                fontSize = 24.sp, // Un poco más pequeño para que no se amontone
                fontWeight = FontWeight.ExtraBold
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = "Notificaciones",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- CONTENEDOR DEL CARRO (DUSTER XL) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.duster),
                contentDescription = null,
                modifier = Modifier.size(500.dp),
                contentScale = ContentScale.Fit
            )

            // CAPA DE ASIENTOS (Usando la misma lógica de coordenadas)
            Box(
                modifier = Modifier.size(500.dp),
                contentAlignment = Alignment.Center
            ) {
                // Silla 1 (Lugar del conductor/copiloto según tu lógica)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 1 },
                    estaSeleccionado = sillaSeleccionada == 1,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(bottom = 60.dp, start = 175.dp)
                )

                // Silla 2 (Trasera Izquierda)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 2 },
                    estaSeleccionado = sillaSeleccionada == 2,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 165.dp, start = 175.dp)
                )

                // Silla 3 (Trasera Derecha)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 3 },
                    estaSeleccionado = sillaSeleccionada == 3,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 165.dp, end = 175.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- BOTÓN CONFIRMAR REGRESO ---
        Button(
            onClick = { /* Confirmar e ir al Resumen Final */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaCajas),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = if (sillaSeleccionada != -1) "CONFIRMAR REGRESO (Silla $sillaSeleccionada)" else "CONFIRMAR SILLA DE VUELTA",
                color = verdeFondo,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SillaregresoPreview() {
    SillaregresoScreen()
}