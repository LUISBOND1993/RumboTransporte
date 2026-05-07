package com.example.rumboapp

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.rumboapp.R

@Composable
fun Informacion(
    onBackClick: () -> Unit,
    onHomeClick: () -> Unit,
    onVerVehiculoClick: () -> Unit,
    onChatClick: () -> Unit,
    onVerConductorClick: () -> Unit
) {
    val verdeApp = Color(0xFF2E3D24)
    val doradoApp = Color(0xFFD4B25A)

    // 1. Obtenemos el contexto para lanzar el Intent
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeApp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carretera),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.clickable { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color.Black,
                            modifier = Modifier.size(35.dp).padding(8.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.Black,
                        modifier = Modifier.clickable { onHomeClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Inicio",
                            tint = Color.White,
                            modifier = Modifier.size(45.dp).padding(10.dp)
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.profesional),
                        contentDescription = "Ver perfil",
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { onVerConductorClick() },
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Andres Molina",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            repeat(5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = doradoApp,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Text(
                            text = "4.7 (320)",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(doradoApp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.carrito),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "ABC - 123",
                    modifier = Modifier.padding(12.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onVerVehiculoClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = doradoApp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.DirectionsCar, null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Ver vehiculo", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            // BOTÓN MODIFICADO PARA WHATSAPP
            Button(
                onClick = {
                    // 2. Definimos el número y el mensaje (Formato internacional sin el +)
                    val telefono = "573057498002"
                    val mensaje = "Hola ! Te contacto desde RumboApp, estoy interesado en un viaje."
                    val uri = Uri.parse("https://api.whatsapp.com/send?phone=$telefono&text=$mensaje")

                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)

                    // También llamamos a la función original por si tienes lógica extra
                    onChatClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = doradoApp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Person, null, tint = Color.Black)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Chatear con el conductor", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InformacionPreview() {
    com.example.rumboapp.ui.theme.RumboAppTheme {
        Informacion(
            onBackClick = { },
            onHomeClick = { },
            onVerVehiculoClick = { },
            onChatClick = { },
            onVerConductorClick = { }
        )
    }
}