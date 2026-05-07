package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.R

@Composable
fun InformacionConductor() {
    val verdeFondo = Color(0xFF2E3D24)
    val doradoContenedor = Color(0xFFE6D3A3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeFondo)
            .verticalScroll(rememberScrollState())
    ) {
        // Toolbar Superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(50),
                color = Color.White.copy(alpha = 0.8f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp).size(24.dp)
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(10.dp).size(24.dp)
                )
            }
        }

        Text(
            text = "Información del\nConductor",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
        )

        // Card de Perfil Principal
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp)),
            color = doradoContenedor
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profesional),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("Andrés Molina", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_car_check), // Usa tu ic_verified aquí
                            contentDescription = null,
                            tint = Color(0xFF4CAF50),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Conductor Verificado", fontSize = 14.sp)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) {
                            Icon(Icons.Default.Star, null, tint = Color(0xFFD4B25A), modifier = Modifier.size(16.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("4.7 (320)", fontSize = 12.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección Información Personal
        ContenedorInfo(titulo = "Información personal", iconoTitulo = R.drawable.ic_user, colorBase = doradoContenedor) {
            ItemDetalle("Nombre:", "Andrés Molina", R.drawable.ic_user_detail)
            ItemDetalle("Cc:", "1.234.567.890", R.drawable.ic_id_card)
            ItemDetalle("Edad:", "34 años", R.drawable.ic_age)
            ItemDetalle("Ciudad de residencia:", "Bogotá", R.drawable.ic_city)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sección Contacto
        ContenedorInfo(titulo = "Contacto", iconoTitulo = R.drawable.ic_contact_book, colorBase = doradoContenedor) {
            ItemDetalle("Celular:", "300 123 4567", R.drawable.ic_phone)
            ItemDetalle("Email:", "andres.molina@email.com", R.drawable.ic_email)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Sección Experiencia
        ContenedorInfo(titulo = "Información del conductor", iconoTitulo = R.drawable.ic_driver_info, colorBase = doradoContenedor) {
            ItemDetalle("Años de experiencia:", "6 años", R.drawable.ic_experience)
            ItemDetalle("Viajes realizados:", "320+", R.drawable.ic_trips)
            ItemDetalle("Calificación:", "4.7 / 5", R.drawable.ic_rating_stat)
        }

        // Botón Final
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4B25A)),
            shape = RoundedCornerShape(10.dp)
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_user), contentDescription = null, tint = Color.Black)
            Spacer(modifier = Modifier.width(5.dp))
            Text("Chatear con el conductor", color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConductorPreview() {
    InformacionConductor()
}