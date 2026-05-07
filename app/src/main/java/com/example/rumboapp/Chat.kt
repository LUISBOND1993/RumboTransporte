package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.R

// Definimos los colores para las burbujas según tu diseño
val BurbujaConductor = Color(0xFFE6D3A3) // Dorado clarito
val BurbujaUsuario = Color(0xFFD4B25A)    // Dorado fuerte

data class Mensaje(val texto: String, val esMio: Boolean, val hora: String)

@Composable
fun ChatScreen() {
    val verdeFondo = Color(0xFF2E3D24)

    // Datos de ejemplo para el prototipo
    val listaMensajes = listOf(
        Mensaje("Hola Maria, Estoy por llegar al punto de encuentro.", false, "11:38"),
        Mensaje("Perfecto. Te estaré esperando!", true, "11:39")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
    ) {
        // Cabecera del Chat
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
            }
            Image(
                painter = painterResource(id = R.drawable.profesional),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Andrés", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_chat_header), // Tu nuevo icono
                contentDescription = null,
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(28.dp)
            )
        }

        // Cuerpo del Chat (Lista de mensajes)
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            reverseLayout = false
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                    Surface(color = Color.Black, shape = RoundedCornerShape(8.dp)) {
                        Text("Hoy", color = Color.White, modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp), fontSize = 12.sp)
                    }
                }
            }
            items(listaMensajes) { mensaje ->
                ChatBubble(mensaje)
            }
        }

        // Sugerencias Rápidas
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(BurbujaConductor)
                .padding(8.dp)
        ) {
            SugerenciaItem("Ya voy saliendo")
            SugerenciaItem("Estoy en el punto de encuentro")
            SugerenciaItem("¿En dónde estás?")
        }

        // Input de Mensaje (Barra inferior)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Enviar mensaje...") },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = BurbujaConductor,
                    unfocusedContainerColor = BurbujaConductor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            FloatingActionButton(
                onClick = { },
                containerColor = Color(0xFF1B2616),
                modifier = Modifier.size(50.dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Send, null, tint = Color(0xFF4CAF50))
            }
        }
    }
}

@Composable
fun ChatBubble(mensaje: Mensaje) {
    val alineacion = if (mensaje.esMio) Alignment.CenterEnd else Alignment.CenterStart
    val colorBurbuja = if (mensaje.esMio) BurbujaUsuario else BurbujaConductor
    val forma = if (mensaje.esMio) {
        RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
    }

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), contentAlignment = alineacion) {
        Column(horizontalAlignment = if (mensaje.esMio) Alignment.End else Alignment.Start) {
            Surface(
                color = colorBurbuja,
                shape = forma
            ) {
                Text(
                    text = mensaje.texto,
                    modifier = Modifier.padding(12.dp),
                    color = Color.Black,
                    fontSize = 15.sp
                )
            }
            Text(mensaje.hora, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

@Composable
fun SugerenciaItem(texto: String) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        color = Color.Black,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(texto, color = Color.White, modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatPreview() {
    ChatScreen()
}