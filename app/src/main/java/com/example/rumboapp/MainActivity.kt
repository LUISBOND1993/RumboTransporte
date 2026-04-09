package com.example.rumboapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rumboapp.ui.theme.RumboAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RumboAppTheme {
                val navController = rememberNavController() // El control remoto de las pantallas

                NavHost(navController = navController, startDestination = "welcome") {
                    // Ruta 1: Bienvenida
                    composable("welcome") {
                        WelcomeScreen(onIngresarClick = {
                            navController.navigate("login") {
                                // Esto evita que el Debugger se "trabe" al intentar
                                // abrir varias veces la misma pantalla
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }
                    // Ruta 2: Login
                    composable("login") {
                        LoginScreen(
                            onForgotPasswordClick = {
                                navController.navigate("recupera_password") // Así se llama tu nueva pantalla
                            }
                        )
                    }

                    composable("recupera_password") {
                        RecuperaPasswordScreen(
                            onBackClick = { navController.popBackStack() },
                            onSendCodeClick = {
                                // Aquí podrías navegar a una pantalla de "Código Enviado" después
                                android.util.Log.d("RUMBO_DEBUG", "Código enviado")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onIngresarClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // La imagen va PRIMERO para que sea el fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_bienvenidaapp),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // La Column va DESPUÉS para que esté "encima" de la imagen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Subimos un poco el botón
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    // 1. Verificamos en Logcat
                    android.util.Log.d("RUMBO_DEBUG", "Ejecutando navegación optimizada...")

                    // 2. Ejecutamos la navegación
                    onIngresarClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp)
            ) {
                Text("INGRESAR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomePreview() {
    RumboAppTheme {
        WelcomeScreen(onIngresarClick = { })
    }
}
