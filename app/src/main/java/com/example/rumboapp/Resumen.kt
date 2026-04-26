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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ResumenScreen(
    origen: String,
    destino: String,
    fecha: String,
    sillaNumero: String,
    onBackClick: () -> Unit,
    onRegresoClick: () -> Unit,
    onConfirmarPagarClick: () -> Unit // NUEVO: Callback para iniciar el flujo de pago
) {
    val verdeFondo = Color(0xFF2D461E)
    val cremaTarjeta = Color(0xFFE8D596)
    val cafeTexto = Color(0xFF2D2D2D)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .padding(horizontal = 30.dp, vertical = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
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

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "RESUMEN",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(cremaTarjeta)
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_car_check),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = cafeTexto
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Detalles del Viaje",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = cafeTexto
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = cafeTexto.copy(alpha = 0.2f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))

            ResumenRow(R.drawable.ic_location, "Origen:", origen)
            ResumenRow(R.drawable.ic_destination, "Destino:", destino)
            ResumenRow(R.drawable.ic_calendar, "Fecha:", fecha)
            ResumenRow(R.drawable.ic_clock, "Hora:", "08:00 AM")
            ResumenRow(R.drawable.ic_seat_choice, "Silla:", "Asiento $sillaNumero")
            ResumenRow(R.drawable.ic_price_tag, "Precio:", "$68.000") // Actualizado para ser consistente con el módulo de pagos
        }


        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.duster),
                contentDescription = null,
                modifier = Modifier.size(350.dp),
                contentScale = ContentScale.Fit
            )
        }


        Button(
            onClick = { onRegresoClick() },
            modifier = Modifier.fillMaxWidth(0.9f).height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = cremaTarjeta),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("Apartar viaje de regreso", color = cafeTexto, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = { onConfirmarPagarClick() }, // Usamos el nuevo callback
            modifier = Modifier.fillMaxWidth(0.9f).height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Text("Confirmar y Pagar", color = verdeFondo, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun ResumenRow(iconId: Int, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xFF2D2D2D)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2D2D2D))
        }

        Box(
            modifier = Modifier
                .weight(1.2f)
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White.copy(alpha = 0.6f))
                .padding(horizontal = 10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ResumenPreview() {
    ResumenScreen(
        origen = "Villavicencio",
        destino = "Acacías",
        fecha = "20 de Marzo",
        sillaNumero = "2",
        onBackClick = {},
        onRegresoClick = {},
        onConfirmarPagarClick = {}
    )
}
