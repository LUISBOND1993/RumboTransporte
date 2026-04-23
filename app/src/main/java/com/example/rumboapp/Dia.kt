package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border // ESTE ERA EL IMPORT QUE FALTABA
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun DiaScreen(ciudadDestino: String, onBackClick: () -> Unit, onDiaConfirmado: (Int) -> Unit) {
    val verdeFondoCalendario = Color(0xFF2D461E)
    val verdeBotonesMes = Color(0xFF4C6636)
    val cremaCirculo = Color(0xFFE8D596)
    var esSoloIda by remember { mutableStateOf(true) }

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
                .padding(horizontal = 25.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header: Botón atrás y Usuario
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable { onBackClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Atrás",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Unspecified
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null,
                    modifier = Modifier.size(45.dp),
                    tint = Color.Unspecified
                )
            }

            Text(
                text = "COMPRA TU PASAJE",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(vertical = 15.dp)
            )

            // Selectores Ida/Regreso
            Column(modifier = Modifier.fillMaxWidth()) {
                DiaIdaFilaOpcion("SOLO IDA", esSoloIda, verdeFondoCalendario) { esSoloIda = true }
                DiaIdaFilaOpcion("IDA Y REGRESO", !esSoloIda, verdeFondoCalendario) { esSoloIda = false }
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Direcciones con el icono de grupo lateral
            Row(verticalAlignment = Alignment.Bottom) {
                Column(modifier = Modifier.weight(1f)) {
                    DiaIdaCajaDireccion("DIRECCIÓN DE ORIGEN", "", verdeFondoCalendario)
                    Spacer(modifier = Modifier.height(10.dp))
                    DiaIdaCajaDireccion("INGRESA TU DESTINO:", ciudadDestino, verdeFondoCalendario)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier.size(35.dp),
                        tint = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(modifier = Modifier.size(35.dp).clip(RoundedCornerShape(8.dp)).background(verdeFondoCalendario))
                }
            }

            Text(
                "FECHA DE IDA",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
            )

            // Selector de Mes
            Row(verticalAlignment = Alignment.CenterVertically) {
                DiaIdaBotonMes("Enero", false, verdeBotonesMes)
                DiaIdaBotonMes("Febrero", false, verdeBotonesMes)
                DiaIdaBotonMes("Marzo", true, Color.White)
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp).padding(start = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            // Calendario con diseño de círculos crema
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = verdeFondoCalendario.copy(alpha = 0.92f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb").forEach {
                            Text(it, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    for (fila in 0 until 5) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                            for (col in 0 until 7) {
                                val diaActual = fila * 7 + col + 1
                                if (diaActual <= 31) {
                                    DiaIdaItemCalendario(
                                        dia = diaActual,
                                        seleccionado = (diaActual == 13),
                                        colorCrema = cremaCirculo,
                                        colorVerde = verdeFondoCalendario
                                    ) { onDiaConfirmado(diaActual) }
                                } else {
                                    Spacer(modifier = Modifier.size(35.dp))
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
fun DiaIdaFilaOpcion(texto: String, seleccionado: Boolean, color: Color, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp).clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .let {
                    if (seleccionado) it.background(color)
                    else it.border(2.dp, color, CircleShape)
                }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(texto, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun DiaIdaCajaDireccion(label: String, ciudad: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Surface(
            modifier = Modifier.fillMaxWidth().height(38.dp),
            color = color,
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(contentAlignment = Alignment.CenterStart) {
                Text(ciudad, color = Color.White, modifier = Modifier.padding(start = 15.dp), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DiaIdaBotonMes(texto: String, activo: Boolean, colorFondo: Color) {
    Surface(
        modifier = Modifier.padding(horizontal = 4.dp),
        color = if (activo) colorFondo else colorFondo.copy(alpha = 0.5f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = texto,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
            color = if (activo && texto == "Marzo") Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Composable
fun DiaIdaItemCalendario(dia: Int, seleccionado: Boolean, colorCrema: Color, colorVerde: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(CircleShape)
            .let {
                if (seleccionado) it.border(2.dp, colorCrema, CircleShape)
                else it.background(colorCrema)
            }
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dia.toString(),
            color = if (seleccionado) colorCrema else colorVerde,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun DiaScreenPreview() {
    DiaScreen(ciudadDestino = "Acacías", onBackClick = {}, onDiaConfirmado = {})
}