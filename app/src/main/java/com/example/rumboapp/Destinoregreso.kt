package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
fun DestinoregresoScreen() {
    val verdeFondoCuadro = Color(0xFF2D461E)
    val cremaTarjetas = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        // Fondo de Paisaje
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
            // Header
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
                        modifier = Modifier.size(24.dp).rotate(180f),
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

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(verdeFondoCuadro)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text("IDA Y REGRESO", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Labels
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("ORIGEN:", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(verdeFondoCuadro)
                )
                Spacer(modifier = Modifier.height(15.dp))
                Text("BUSCA TU DESTINO DE REGRESO:", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tarjeta Verde de Ciudades
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = verdeFondoCuadro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CajaCiudad(texto = "Buscar ciudad de regreso", colorFondo = cremaTarjetas, icono = {
                        Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    })

                    Spacer(modifier = Modifier.height(15.dp))

                    val ciudades = listOf("Villavicencio", "Acacías", "Restrepo", "Cumaral", "Castilla")

                    ciudades.forEach { ciudad ->
                        CajaCiudad(
                            texto = ciudad,
                            colorFondo = cremaTarjetas,
                            mostrarEstrella = (ciudad == "Villavicencio")
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Se eliminó la Row de paginación (1, 2, >) para que el diseño quede más limpio
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DestinoregresoPreview() {
    DestinoregresoScreen()
}