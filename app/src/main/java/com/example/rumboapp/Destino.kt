package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
fun DestinoScreen() {
    val verdeFondoCuadro = Color(0xFF2D461E)
    val cremaTarjetas = Color(0xFFE8D596)
    val naranjaEstrella = Color(0xFFFFB800)

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Fondo de Paisaje
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
            // Iconos superiores
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
                        .clickable { /* Volver */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(45.dp).clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "COMPRA TU PASAJE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            // Opción seleccionada (Solo ida)
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(verdeFondoCuadro)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("SOLO IDA", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Labels de Origen y Destino
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("ORIGEN:", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(verdeFondoCuadro)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("INGRESA TU DESTINO:", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // --- CONTENEDOR VERDE DE CIUDADES ---
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = verdeFondoCuadro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Barra de Buscar
                    CajaCiudad(texto = "Buscar", colorFondo = cremaTarjetas, icono = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    })

                    Spacer(modifier = Modifier.height(15.dp))

                    // Lista de ciudades del Meta
                    val ciudades = listOf("Acacías", "Granada", "Puerto López", "San Martín", "Villavicencio")

                    ciudades.forEach { ciudad ->
                        CajaCiudad(
                            texto = ciudad,
                            colorFondo = cremaTarjetas,
                            mostrarEstrella = (ciudad == "Acacías")
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Paginación (1 2 >)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Surface(
                            color = cremaTarjetas,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("1", fontWeight = FontWeight.Bold, color = verdeFondoCuadro)
                            }
                        }
                        Spacer(modifier = Modifier.width(15.dp))
                        Text("2", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(">", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun CajaCiudad(
    texto: String,
    colorFondo: Color,
    mostrarEstrella: Boolean = false,
    icono: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        color = colorFondo,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icono != null) {
                icono()
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = texto,
                color = if (texto == "Buscar") Color.Gray else Color.Black,
                fontSize = 14.sp,
                fontWeight = if (texto == "Buscar") FontWeight.Normal else FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            if (mostrarEstrella) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DestinoPreview() {
    DestinoScreen()
}
