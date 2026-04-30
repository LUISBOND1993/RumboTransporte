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
fun ResumenfinalScreen(
    // Datos Ida
    origenIda: String,
    destinoIda: String,
    fechaIda: String,
    horaIda: String,
    sillaIda: String,
    precioIda: String,
    // Datos Regreso
    origenRegreso: String,
    destinoRegreso: String,
    fechaRegreso: String,
    horaRegreso: String,
    sillaRegreso: String,
    precioRegreso: String,
    // Acciones
    onBackClick: () -> Unit,
    onConfirmarPagoClick: () -> Unit
) {
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
            // Botón Atrás
            Row(modifier = Modifier.fillMaxWidth().padding(top = 35.dp)) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Cuadro Verde Exterior
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = verdeBorde,
                shape = RoundedCornerShape(30.dp)
            ) {
                // Cuadro Crema Interior
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
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = cafeTexto
                        )
                        HorizontalDivider(modifier = Modifier.width(50.dp), color = cafeTexto)

                        Spacer(modifier = Modifier.height(15.dp))

                        // SECCIÓN IDA
                        Text("VIAJE IDA", fontWeight = FontWeight.Black, fontSize = 16.sp)
                        ContenidoResumen(
                            datos = listOf(origenIda, destinoIda, fechaIda, horaIda, "Asiento $sillaIda", precioIda)
                        )

                        Spacer(modifier = Modifier.height(15.dp))
                        HorizontalDivider(color = cafeTexto.copy(alpha = 0.1f))
                        Spacer(modifier = Modifier.height(15.dp))

                        // SECCIÓN REGRESO
                        Text("VIAJE DE REGRESO", fontWeight = FontWeight.Black, fontSize = 16.sp)
                        ContenidoResumen(
                            datos = listOf(origenRegreso, destinoRegreso, fechaRegreso, horaRegreso, "Asiento $sillaRegreso", precioRegreso)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Botón de Pago
            Button(
                onClick = { onConfirmarPagoClick() },
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
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
fun ContenidoResumen(datos: List<String>) {
    val etiquetas = listOf("Origen:", "Destino:", "Fecha:", "Hora:", "Silla:", "Precio:")
    val iconos = listOf(
        R.drawable.ic_location, R.drawable.ic_map_route,
        R.drawable.ic_calendarioa, R.drawable.ic_clock,
        R.drawable.ic_seat_choice, R.drawable.ic_price_tag
    )

    Column(modifier = Modifier.fillMaxWidth().padding(top = 5.dp)) {
        etiquetas.forEachIndexed { index, texto ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icono y Etiqueta
                Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = iconos[index]),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(texto, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                // Cuadro Blanco con el valor real
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .height(26.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White.copy(alpha = 0.9f))
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = datos.getOrElse(index) { "" },
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp"
)
@Composable
fun ResumenfinalPreview() {
    ResumenfinalScreen(
        origenIda = "Villavicencio",
        destinoIda = "Acacías",
        fechaIda = "20 de Marzo",
        horaIda = "08:00 AM",
        sillaIda = "12",
        precioIda = "$160.000",
        origenRegreso = "Acacías",
        destinoRegreso = "Villavicencio",
        fechaRegreso = "25 de Marzo",
        horaRegreso = "04:00 PM",
        sillaRegreso = "05",
        precioRegreso = "$160.000",
        onBackClick = {},
        onConfirmarPagoClick = {}
    )
}