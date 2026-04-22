package com.example.rumboapp

import androidx.compose.foundation.*
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DestinoScreen(onBackClick: () -> Unit, onCiudadEscogida: (String) -> Unit) {
    val verdeFondoCuadro = Color(0xFF2D461E)
    val cremaTarjetas = Color(0xFFE8D596)

    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.fondo_paisaje), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 25.dp, vertical = 40.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                IconButton(onClick = onBackClick, modifier = Modifier.background(Color.White.copy(0.8f), CircleShape)) {
                    Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null, modifier = Modifier.rotate(180f))
                }
                Image(painter = painterResource(id = R.drawable.ic_user), contentDescription = null, modifier = Modifier.size(45.dp).clip(CircleShape))
            }

            Text("COMPRA TU PASAJE", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(vertical = 15.dp))

            // Se usa el nombre local para evitar el error de referencia
            DestinoFilaOpcion("SOLO IDA", true, verdeFondoCuadro)

            Spacer(modifier = Modifier.height(20.dp))

            Text("ORIGEN:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Box(modifier = Modifier.fillMaxWidth().height(35.dp).background(verdeFondoCuadro, RoundedCornerShape(20.dp)))

            Spacer(modifier = Modifier.height(10.dp))
            Text("INGRESA TU DESTINO:", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)

            Surface(modifier = Modifier.fillMaxWidth().weight(1f).padding(top = 10.dp), color = verdeFondoCuadro.copy(0.9f), shape = RoundedCornerShape(24.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    CajaCiudad("Buscar", cremaTarjetas, icono = { Icon(Icons.Default.Search, null, tint = Color.Gray) })

                    val ciudades = listOf("Acacías", "Granada", "Puerto López", "San Martín", "Villavicencio")
                    ciudades.forEach { ciudad ->
                        Spacer(modifier = Modifier.height(10.dp))
                        CajaCiudad(
                            texto = ciudad,
                            colorFondo = cremaTarjetas,
                            mostrarEstrella = (ciudad == "Acacías"),
                            onClick = { onCiudadEscogida(ciudad) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CajaCiudad(texto: String, colorFondo: Color, mostrarEstrella: Boolean = false, icono: @Composable (() -> Unit)? = null, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(40.dp).clickable { onClick() },
        color = colorFondo,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(modifier = Modifier.padding(horizontal = 15.dp), verticalAlignment = Alignment.CenterVertically) {
            icono?.invoke()
            Text(texto, color = if (texto == "Buscar") Color.Gray else Color.Black, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            if (mostrarEstrella) Icon(Icons.Default.Star, null, tint = Color(0xFFFFB800))
        }
    }
}

// Función agregada localmente para solucionar el error 'Unresolved reference'
@Composable
fun DestinoFilaOpcion(texto: String, seleccionado: Boolean, color: Color, onClick: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 4.dp)
    ) {
        Box(modifier = Modifier.size(22.dp).clip(CircleShape).background(if (seleccionado) color else Color.Gray.copy(0.4f)))
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
    }
}