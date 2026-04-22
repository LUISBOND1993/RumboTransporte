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
fun SillaScreen() {
    val verdeFondo = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

    // Estado para la silla seleccionada
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
            // BOTÓN VOLVER
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

            // TÍTULO GRANDE (28sp)
            Text(
                text = "ELIGE TU SILLA",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // --- ¡NUEVO ICONO DE CAMPANA! ---
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .clickable { /* Acción Notificaciones */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    // Reemplazado ic_clock por ic_bell
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = "Notificaciones",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- CONTENEDOR DEL CARRO (TAMAÑO MÁXIMO QUE ACORDAMOS) ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            // IMAGEN DUSTER XL
            Image(
                painter = painterResource(id = R.drawable.duster),
                contentDescription = null,
                modifier = Modifier.size(500.dp),
                contentScale = ContentScale.Fit
            )

            // CAPA DE ASIENTOS CON COORDENADAS FIJAS
            Box(
                modifier = Modifier.size(500.dp),
                contentAlignment = Alignment.Center
            ) {
                // CONDUCTOR (Vuelve a la Izquierda)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 0 },
                    estaSeleccionado = sillaSeleccionada == 0,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(bottom = 60.dp, start = 175.dp) // <--- El start que cuadró
                )

                // TRASERO IZQUIERDO (Tal cual)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 1 },
                    estaSeleccionado = sillaSeleccionada == 1,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(top = 165.dp, start = 175.dp) // <--- El padding trasero
                )

                // TRASERO DERECHO (Tal cual)
                PosicionAsientoItem(
                    onClick = { sillaSeleccionada = 2 },
                    estaSeleccionado = sillaSeleccionada == 2,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 165.dp, end = 175.dp) // <--- El padding trasero
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- BOTÓN CONFIRMAR ---
        Button(
            onClick = { /* Acción Confirmar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaCajas),
            shape = RoundedCornerShape(15.dp)
        ) {
            Text(
                text = if (sillaSeleccionada != -1) "CONFIRMAR ASIENTO ${sillaSeleccionada + 1}" else "CONFIRMAR ASIENTO",
                color = verdeFondo,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun PosicionAsientoItem(onClick: () -> Unit, estaSeleccionado: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(55.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.verdes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            // Resaltamos el icono cuando se selecciona
            alpha = if (estaSeleccionado) 1f else 0.7f
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SillaPreview() {
    SillaScreen()
}