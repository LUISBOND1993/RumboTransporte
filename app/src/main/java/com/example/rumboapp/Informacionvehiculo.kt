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

@Composable
fun InformacionVehiculo() {
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

        // Imagen del Vehículo
        Image(
            painter = painterResource(id = R.drawable.carrito),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )

        Text(
            text = "Información del\nVehículo",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        // Contenedor Seguridad
        ContenedorInfo(
            titulo = "Seguridad",
            iconoTitulo = R.drawable.ic_car_check, // Usando uno existente de tu lista
            colorBase = doradoContenedor
        ) {
            // He usado ic_car_check para todos temporalmente para que el Split cargue sin errores
            ItemDetalle("Airbags:", "Sí", R.drawable.ic_security)
            ItemDetalle("Frenos ABS:", "Sí", R.drawable.ic_brakes)
            ItemDetalle("Cinturones de seguridad:", "5", R.drawable.ic_seatbelt)
            ItemDetalle("Vehículo en buen estado:", "✅", R.drawable.ic_car_check)
            ItemDetalle("Revisión técnico-mecánica:", "Vigente", R.drawable.ic_document)
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Contenedor Detalles
        ContenedorInfo(
            titulo = "Detalles",
            iconoTitulo = R.drawable.ic_car, // Usando ic_car de tu lista
            colorBase = doradoContenedor
        ) {
            ItemDetalle("Marca y modelo:", "Renault Duster", R.drawable.ic_brand)
            ItemDetalle("Tipo de vehículo:", "SUV", R.drawable.ic_car_type)
            ItemDetalle("Color:", "Blanco", R.drawable.ic_palette)
            ItemDetalle("Placa:", "ABC - 123", R.drawable.ic_license_plate)
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun ContenedorInfo(
    titulo: String,
    iconoTitulo: Int,
    colorBase: Color,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(colorBase)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = iconoTitulo),
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = titulo,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        HorizontalDivider(color = Color.Black, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
        contenido()
    }
}

@Composable
fun ItemDetalle(label: String, valor: String, icono: Int) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = valor, color = Color.Black, fontSize = 14.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VehiculoPreview() {
    InformacionVehiculo()
}