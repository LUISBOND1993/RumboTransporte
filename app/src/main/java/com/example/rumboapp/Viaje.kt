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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ViajeScreen() {
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
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color.Unspecified
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
            }

            Text("SELECCIONA TU VIAJE", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 15.dp))

            // --- RESUMEN VIAJE (VACÍO) ---
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeOscuro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = Modifier.padding(15.dp)) {
                    Text("TU VIAJE", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(10.dp))

                    // Origen
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_location), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(30.dp).clip(RoundedCornerShape(10.dp)).background(cremaCajas))
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    // Destino
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_destination), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(30.dp).clip(RoundedCornerShape(10.dp)).background(cremaCajas))
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Text("Fecha de salida", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.CenterHorizontally))
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally), verticalAlignment = Alignment.CenterVertically) {
                        Icon(painterResource(id = R.drawable.ic_calendar), contentDescription = null, tint = Color.Unspecified, modifier = Modifier.size(35.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(modifier = Modifier.width(100.dp).height(25.dp), color = Color.White, shape = RoundedCornerShape(5.dp)) { }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Surface(color = cremaCajas, shape = RoundedCornerShape(10.dp)) {
                Text(" VIAJES DISPONIBLES ", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp))
            }

            Spacer(modifier = Modifier.height(15.dp))

            // --- LISTA ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                item { ViajeCardCorreccion("160.000", "10:00 am", "2:00 pm", "4h Aprox") }
                item { ViajeCardCorreccion("170.000", "8:00 pm", "12:00 am", "4h Aprox") }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- LOGO RUMBO MÁS GRANDE ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.logo_rumbo),
                    contentDescription = null,
                    modifier = Modifier.size(90.dp) // <-- AHORA ES MÁS GRANDE (ANTES 50)
                )
                Text("RUMBO", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp) // <-- TEXTO TAMBIÉN MÁS GRANDE
            }
        }
    }
}

@Composable
fun ViajeCardCorreccion(precio: String, salida: String, llegada: String, duracion: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable { },
        color = Color(0xFF2D461E).copy(alpha = 0.9f),
        shape = RoundedCornerShape(15.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            // Usamos una Row para separar el texto del carro
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) { // <-- ESTO ES CLAVE: LE DAMOS PESO AL TEXTO
                    Text("Precio $$precio", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Text("SALIDA", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(salida, color = Color.White)
                            Icon(painterResource(id = R.drawable.ic_clock), null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }

                        Text(" ———> ", color = Color.White, modifier = Modifier.padding(horizontal = 8.dp))

                        Column(horizontalAlignment = Alignment.End) {
                            Text("LLEGADA", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text(llegada, color = Color.White)
                            Icon(painterResource(id = R.drawable.ic_clock), null, tint = Color.White, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(modifier = Modifier.clickable { }, color = Color(0xFFE8D596), shape = RoundedCornerShape(8.dp)) {
                        Text(" Ver Sillas ", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold)
                    }
                }

                // --- EL CARRO AHORA ESTÁ EN SU PROPIA COLUMNA ---
                Image(
                    painter = painterResource(id = R.drawable.ic_car_black),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp) // <-- MÁS GRANDE TAMBIÉN
                        .padding(start = 10.dp) // <-- ESPACIO PARA NO PISAR EL TEXTO
                        .clickable { }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ViajePreview() {
    ViajeScreen()
}