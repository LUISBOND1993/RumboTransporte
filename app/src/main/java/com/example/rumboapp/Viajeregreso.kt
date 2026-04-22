package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun ViajeregresoScreen() {
    val verdeOscuro = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

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
                .padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                        // Mantenemos la rotación corregida
                        modifier = Modifier.size(24.dp).rotate(180f),
                        tint = Color.Unspecified
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
            }

            Text("SELECCIONA TU REGRESO", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 15.dp))

            // --- RESUMEN VIAJE ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeOscuro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text("TU VIAJE DE VUELTA", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_location), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(30.dp).clip(RoundedCornerShape(10.dp)).background(cremaCajas)) {
                            Text("Acacías", modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold, color = verdeOscuro)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_destination), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(30.dp).clip(RoundedCornerShape(10.dp)).background(cremaCajas)) {
                            Text("Villavicencio", modifier = Modifier.align(Alignment.Center), fontWeight = FontWeight.Bold, color = verdeOscuro)
                        }
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Text("Fecha de regreso", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_calendar), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(35.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(modifier = Modifier.width(120.dp).height(25.dp), color = Color.White, shape = RoundedCornerShape(5.dp)) {
                            Text("25/03/2026", fontSize = 14.sp, modifier = Modifier.wrapContentSize(Alignment.Center))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Surface(color = cremaCajas, shape = RoundedCornerShape(10.dp)) {
                Text(" REGRESOS DISPONIBLES ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
            }

            Spacer(modifier = Modifier.height(15.dp))

            // --- LISTA DE VIAJES DE VUELTA CORREGIDA ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                // Aquí quitamos el cuarto parámetro "4h Aprox" para que no de error
                item { ViajeCardCorreccion("160.000", "02:00 pm", "06:00 pm") }
                item { ViajeCardCorreccion("170.000", "05:00 pm", "09:00 pm") }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_rumbo),
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
                Text("RUMBO", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViajeregresoPreview() {
    ViajeregresoScreen()
}