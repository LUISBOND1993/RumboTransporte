package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResumenfinalScreen() {
    val verdeBorde = Color(0xFF2D461E)
    val cremaFondo = Color(0xFFE8D596)
    val cafeTexto = Color(0xFF2D2D2D)
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_paisaje),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 35.dp)) { // Añadido padding para que el botón no choque con la hora del sistema
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { /* Acción para volver */ },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp).rotate(180f),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = verdeBorde,
                shape = RoundedCornerShape(30.dp)
            ) {
                Surface(
                    modifier = Modifier.padding(10.dp).fillMaxSize(),
                    color = cremaFondo,
                    shape = RoundedCornerShape(22.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "RESUMEN",
                            fontSize = 26.sp, // Ajustado para mejor legibilidad
                            fontWeight = FontWeight.Bold,
                            color = cafeTexto
                        )
                        HorizontalDivider(modifier = Modifier.width(50.dp), color = cafeTexto)

                        Spacer(modifier = Modifier.height(15.dp))

                        Text("VIAJE IDA", fontWeight = FontWeight.Black, fontSize = 16.sp)
                        ContenidoResumen()

                        Spacer(modifier = Modifier.height(15.dp))
                        HorizontalDivider(color = cafeTexto.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(15.dp))

                        Text("VIAJE DE REGRESO", fontWeight = FontWeight.Black, fontSize = 16.sp)
                        ContenidoResumen()
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = { /* Lógica de pago */ },
                modifier = Modifier.fillMaxWidth(0.8f).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cremaFondo),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp)
            ) {
                Text("Ir al Pago", color = cafeTexto, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_rumbo),
                contentDescription = "Logo Rumbo",
                modifier = Modifier.size(60.dp) // Reducido un poco para que quepa mejor en pantallas pequeñas
            )
        }
    }
}

@Composable
fun ContenidoResumen() {
    val etiquetas = listOf("Origen:", "Destino:", "Fecha:", "Hora:", "Silla:", "Precio:")
    val iconos = listOf(
        R.drawable.ic_location, R.drawable.ic_map_route,
        R.drawable.ic_calendarioa, R.drawable.ic_clock,
        R.drawable.ic_seat_choice, R.drawable.ic_price_tag
    )

    Column(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        etiquetas.forEachIndexed { index, texto ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(id = iconos[index]), null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .weight(1.5f) // Ajustado para que no se vea tan separado
                        .height(24.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                )
            }
        }
    }
}

// ESTA ES LA PARTE CLAVE PARA VER EL CELULAR REAL
@Preview(
    showBackground = true,
    showSystemUi = true, // Esto activa la barra de estado y navegación
    device = "spec:width=411dp,height=891dp" // Define un tamaño de dispositivo estándar
)
@Composable
fun ResumenfinalPreview() {
    ResumenfinalScreen()
}