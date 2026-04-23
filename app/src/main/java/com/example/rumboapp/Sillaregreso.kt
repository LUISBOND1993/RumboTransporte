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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SillaregresoScreen(
    onBackClick: () -> Unit,
    onConfirmarClick: (Int) -> Unit
) {
    val verdeFondo = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

    // Mantiene la lógica de selección de silla
    var sillaSeleccionada by remember { mutableIntStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 20.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- ENCABEZADO ---
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
                    .clickable { onBackClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Atrás",
                    modifier = Modifier.size(24.dp).rotate(180f),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "SILLA DE REGRESO",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Espacio para equilibrar el Row (o puedes poner el icono de la campana si quieres)
            Box(modifier = Modifier.size(45.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- MAPA DE SILLAS (EL VEHÍCULO) ---
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

            Box(modifier = Modifier.size(500.dp), contentAlignment = Alignment.Center) {
                // Silla 1
                PosicionAsientoItem(
                    numero = 1,
                    onClick = { sillaSeleccionada = 1 },
                    estaSeleccionado = sillaSeleccionada == 1,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(bottom = 60.dp, start = 175.dp)
                )

                // Silla 2
                PosicionAsientoItem(
                    numero = 2,
                    onClick = { sillaSeleccionada = 2 },
                    estaSeleccionado = sillaSeleccionada == 2,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 165.dp, start = 175.dp)
                )

                // Silla 3
                PosicionAsientoItem(
                    numero = 3,
                    onClick = { sillaSeleccionada = 3 },
                    estaSeleccionado = sillaSeleccionada == 3,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 165.dp, end = 175.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- BOTÓN DE CONFIRMACIÓN ---
        Button(
            onClick = { if (sillaSeleccionada != -1) onConfirmarClick(sillaSeleccionada) },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaCajas),
            shape = RoundedCornerShape(15.dp),
            enabled = sillaSeleccionada != -1 // El botón solo se activa si hay una silla elegida
        ) {
            Text(
                text = "CONFIRMAR REGRESO",
                color = verdeFondo,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SillaregresoPreview() {
    SillaregresoScreen(
        onBackClick = {},
        onConfirmarClick = {}
    )
}