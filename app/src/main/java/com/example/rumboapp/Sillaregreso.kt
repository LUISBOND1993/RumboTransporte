package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun SillaregresoScreen(
    onBackClick: () -> Unit,
    onConfirmarClick: (Int) -> Unit
) {
    val verdeFondo = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

    var sillaSeleccionada by remember { mutableIntStateOf(-1) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 20.dp, vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            }

            Text(
                text = "SILLA DE REGRESO",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Box(modifier = Modifier.size(45.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.duster),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxWidth = maxWidth
                val boxHeight = maxHeight

                // Silla 1 (Atrás Izquierda)
                PosicionAsientoItem(
                    numero = 1,
                    onClick = { sillaSeleccionada = 1; showError = false },
                    estaSeleccionado = sillaSeleccionada == 1,
                    modifier = Modifier
                        .offset(x = boxWidth * 0.35f, y = boxHeight * 0.65f)
                )

                // Silla 2 (Atrás Derecha)
                PosicionAsientoItem(
                    numero = 2,
                    onClick = { sillaSeleccionada = 2; showError = false },
                    estaSeleccionado = sillaSeleccionada == 2,
                    modifier = Modifier
                        .offset(x = boxWidth * 0.55f, y = boxHeight * 0.65f)
                )

                // Silla 3 (Delantera Derecha - Copiloto)
                PosicionAsientoItem(
                    numero = 3,
                    onClick = { sillaSeleccionada = 3; showError = false },
                    estaSeleccionado = sillaSeleccionada == 3,
                    modifier = Modifier
                        .offset(x = boxWidth * 0.55f, y = boxHeight * 0.42f)
                )
                
                // PUESTO CONDUCTOR
                Box(
                    modifier = Modifier
                        .offset(x = boxWidth * 0.35f, y = boxHeight * 0.42f)
                        .size(45.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = "Conductor",
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        if (showError) {
            Text(
                text = "Por favor, selecciona una silla antes de continuar",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { 
                if (sillaSeleccionada != -1) {
                    onConfirmarClick(sillaSeleccionada) 
                } else {
                    showError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaCajas),
            shape = RoundedCornerShape(15.dp)
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
