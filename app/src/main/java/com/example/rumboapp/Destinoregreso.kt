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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DestinoregresoScreen(
    onBackClick: () -> Unit,
    onCiudadEscogida: (String) -> Unit
) {
    val verdeFondoCuadro = Color(0xFF2D461E)
    val cremaTarjetas = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        // Imagen de fondo con el recurso que mencionaste
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
            // Barra superior: Botón Atrás y Perfil
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
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(24.dp), // Quitamos rotación si el icono ya apunta a la izquierda
                        tint = Color.Unspecified
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "Perfil",
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
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

            // Indicador de modo de viaje
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(verdeFondoCuadro))
                Spacer(modifier = Modifier.width(10.dp))
                Text("IDA Y REGRESO", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(25.dp))

            // Etiquetas de Origen y Búsqueda
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

            // Contenedor de lista de ciudades
            Surface(
                modifier = Modifier.fillMaxWidth().weight(1f),
                color = verdeFondoCuadro.copy(alpha = 0.9f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val ciudades = listOf("Villavicencio", "Acacías", "Restrepo", "Cumaral", "Castilla")
                    ciudades.forEach { ciudad ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onCiudadEscogida(ciudad) },
                            color = cremaTarjetas,
                            shape = RoundedCornerShape(15.dp)
                        ) {
                            Text(
                                text = ciudad,
                                modifier = Modifier.padding(15.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

// --- ESTA ES LA PARTE QUE HABILITA EL SPLIT PREVIEW ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DestinoregresoScreenPreview() {
    DestinoregresoScreen(
        onBackClick = {},
        onCiudadEscogida = {}
    )
}