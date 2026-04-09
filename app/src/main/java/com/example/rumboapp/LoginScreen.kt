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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

@Composable
fun LoginScreen() {
    val verdeOscuro = Color(0xFF1B3011) // Definimos el color para reusarlo

    Column(
        modifier = Modifier
            .fillMaxSize()
            // CAMBIO CLAVE: Cambiamos Color.White por el verde oscuro de tu diseño
            .background(verdeOscuro)
    ) {
        // Parte Superior: Imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White) // Mantenemos blanco detrás de la imagen por si acaso
        ) {
            Image(
                painter = painterResource(id = R.drawable.fondo_bienvenidaapp),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // Parte Inferior: Contenedor Verde
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.5f)
                // Tu offset para eliminar los espacios del redondeo
                .offset(y = (-40).dp),
            color = verdeOscuro,
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Espacio para compensar el offset superior
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¡Qué gusto que estés aquí!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email
                Text(
                    text = "Email:",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 14.sp
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contraseña
                Text(
                    text = "Contraseña:",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Start),
                    fontSize = 14.sp
                )
                TextField(
                    value = "",
                    onValueChange = {},
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botón
                Button(
                    onClick = { /* Acción */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4AF37)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Iniciar Sesión", color = Color.Black, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿No tienes cuenta? Regístrate",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    RumboAppTheme {
        LoginScreen()
    }
}