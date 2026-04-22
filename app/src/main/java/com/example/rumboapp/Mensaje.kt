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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MensajeScreen() {
    val verdeOscuro = Color(0xFF2D461E)
    val cremaAmarillo = Color(0xFFE8D596)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fondo con tu imagen de paisaje
        Image(
            painter = painterResource(id = R.drawable.fondo_paisaje),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Capa oscura para resaltar el texto
        Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, bottom = 40.dp), // Espacio para que respire arriba y abajo
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // TARJETA: Más larga/estrecha (80% del ancho)
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(30.dp))
                    .background(verdeOscuro)
                    .padding(35.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // TÍTULO: Letra más grande (26sp)
                Text(
                    text = "Para que viajemos cómodos, recuerda:",
                    color = cremaAmarillo,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(30.dp))

                val recordatorios = listOf(
                    "Equipaje: Solo maleta de mano.",
                    "¿Maleta extra?: Coordina con el conductor.",
                    "Mascotas: Debes avisar antes.",
                    "Paradas: Comunícalas directamente."
                )

                // LISTA: Letra más grande (20sp)
                recordatorios.forEach { texto ->
                    Row(
                        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("• ", color = Color.White, fontSize = 22.sp)
                        Text(
                            text = texto,
                            color = Color.White,
                            fontSize = 20.sp,
                            lineHeight = 26.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // DESPEDIDA: Más grande (22sp)
                Text(
                    text = "¡Buen viaje hacia el Llano!",
                    color = cremaAmarillo,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
            }

            // BOTÓN CAMPANA PARA VOLVER
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .clip(CircleShape)
                    .background(verdeOscuro)
                    .clickable { /* Acción para volver */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MensajePreview() {
    MensajeScreen()
}