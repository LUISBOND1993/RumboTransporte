package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme
import kotlinx.coroutines.delay

@Composable
fun CodigoVerificacionScreen(onBackToMain: () -> Unit) {

    // LÓGICA DE REDIRECCIÓN AUTOMÁTICA
    // Cuando la pantalla se muestra, espera 5 segundos y regresa al inicio
    LaunchedEffect(Unit) {
        delay(5000) // 5000 milisegundos = 5 segundos
        onBackToMain()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Imagen de Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_cambio_contrasena),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.Crop
        )

        // 2. Contenedor Verde Oscuro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 350.dp)
                .offset(y = (-30).dp)
                .background(
                    color = Color(0xFF2D4B1E),
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Título Actualizado
            Text(
                text = "¡Listo!",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
            Text(
                text = "Revisa tu bandeja de entrada",
                textAlign= TextAlign.Center,
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Cuadro de texto informativo (Cremita)
            Surface(
                color = Color(0xFFD9D9B3),
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Hemos enviado un enlace de recuperación a tu correo electrónico.",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Por favor, revisa tu bandeja de entrada y sigue las instrucciones para restablecer tu contraseña.",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        lineHeight = 20.sp,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Icono de Check o Reloj de espera
            CircularProgressIndicator(
                color = Color(0xFFC4A454),
                strokeWidth = 4.dp,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Serás redirigido al inicio en unos segundos...",
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Botón manual por si el usuario no quiere esperar
            Button(
                onClick = onBackToMain,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4A454)),
                shape = RoundedCornerShape(40.dp)
            ) {
                Text(
                    text = "Volver al Inicio Ahora",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CodigoVerificacionPreview() {
    RumboAppTheme {
        CodigoVerificacionScreen(onBackToMain = {})
    }
}