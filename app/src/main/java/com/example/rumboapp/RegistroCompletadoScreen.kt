package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun RegistroCompletadoScreen(onLoginClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. Imagen de Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_bienvenido),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.55f),
            contentScale = ContentScale.Crop
        )

        // 2. Contenedor Verde Oscuro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 320.dp) // Ajustado para que el verde suba un poco más
                .offset(y = (-30).dp)
                .background(
                    color = Color(0xFF1B3011),
                    shape = RoundedCornerShape(topStart = 80.dp, topEnd = 80.dp)
                )
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "¡Bienvenido!",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Viaja fácil y directo.\nNosotros nos\nencargamos del resto.\n\nViaja tranquilo, llega seguro.",
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start,
                lineHeight = 26.sp,
                modifier = Modifier.fillMaxWidth()
            )

            // Espacio controlado en lugar de weight(1f) para evitar que desaparezca el botón
            Spacer(modifier = Modifier.height(50.dp))

            // Botón Iniciar Sesión con tamaño fijo y visible
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth(0.85f) // Más ancho para que sea fácil de tocar
                    .height(60.dp),      // Altura fija y robusta
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC4A454)),
                shape = RoundedCornerShape(30.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = "Iniciar Sesión",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp
                )
            }

            // Espacio extra al final para asegurar que no pegue al borde inferior
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistroCompletadoPreview() {
    RumboAppTheme {
        RegistroCompletadoScreen(onLoginClick = {})
    }
}