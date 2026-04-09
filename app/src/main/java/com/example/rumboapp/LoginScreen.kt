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
fun LoginScreen(onForgotPasswordClick: () -> Unit) { // <-- Añadimos el parámetro de navegación
    val verdeOscuro = Color(0xFF1B3011)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdeOscuro)
    ) {
        // Parte Superior: Imagen
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
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
                .weight(1.8f) // Aumenté un poco el peso para que quepa todo cómodamente
                .offset(y = (-40).dp),
            color = Color(0xFF2D4B1E),
            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "¡Qué gusto que estés aquí!",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(24.dp))

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

                // --- NUEVO: TEXTO OLVIDASTE CONTRASEÑA ---
                TextButton(
                    onClick = onForgotPasswordClick,
                    modifier = Modifier.align(Alignment.End) // Lo alineamos a la derecha
                ) {
                    Text(
                        text = "¿Olvidaste tu contraseña?",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Iniciar Sesión
                Button(
                    onClick = { /* Acción Login */ },
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
        LoginScreen(onForgotPasswordClick = {})
    }
}