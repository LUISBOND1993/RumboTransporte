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

@Composable
fun ViajeregresoScreen(
    origen: String,
    destino: String,
    fecha: String,
    onBackClick: () -> Unit,
    onVerSillasClick: (String, String) -> Unit // Recibe precio y hora
) {
    val verdeOscuro = Color(0xFF2D461E)
    val cremaCajas = Color(0xFFE8D596)

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
                .padding(horizontal = 20.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
            }

            Text(
                text = "DETALLES DEL REGRESO",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 15.dp)
            )


            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeOscuro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(15.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "TU REGRESO",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    ResumenItem(R.drawable.ic_location, origen, cremaCajas)
                    Spacer(modifier = Modifier.height(10.dp))
                    ResumenItem(R.drawable.ic_destination, destino, cremaCajas)

                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Fecha de regreso",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_calendar),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(35.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            modifier = Modifier.width(120.dp).height(25.dp),
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(text = fecha, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            Surface(color = cremaCajas, shape = RoundedCornerShape(10.dp)) {
                Text(
                    text = " VIAJES DISPONIBLES ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // --- LISTA DE VIAJES ---
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                item { ViajeCardCorreccion("160.000", "10:00 am", "2:00 pm", onVerSillasClick) }
                item { ViajeCardCorreccion("170.000", "8:00 pm", "12:00 am", onVerSillasClick) }
            }

            Spacer(modifier = Modifier.weight(1f))

            // --- LOGO FINAL ---
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painter = painterResource(id = R.drawable.logo_rumbo), contentDescription = null, modifier = Modifier.size(90.dp))
                Text("RUMBO", fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            }
        }
    }
}
