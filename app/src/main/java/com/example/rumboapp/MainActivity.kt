package com.example.rumboapp

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
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
                val navController = rememberNavController()
                val context = LocalContext.current

                NavHost(navController = navController, startDestination = "welcome") {

                    // 1. Pantalla de Bienvenida
                    composable("welcome") {
                        WelcomeScreen(onIngresarClick = {
                            navController.navigate("login") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }

                    // 2. Pantalla de Login
                    composable("login") {
                        LoginScreen(
                            onForgotPasswordClick = {
                                navController.navigate("recupera_password")
                            },
                            onRegisterClick = {
                                navController.navigate("registro")
                            },
                            onLoginSuccess = {
                                // Aquí podrías navegar a una Home futura
                                navController.navigate("welcome") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. Pantalla de Recuperar Contraseña (MODIFICADO)
                    composable("recupera_password") {
                        RecuperaPasswordScreen(
                            onBackClick = { navController.popBackStack() },
                            onNavigateToConfirmation = {
                                // Navegamos a la pantalla de aviso de éxito
                                navController.navigate("codigo_verificacion")
                            }
                        )
                    }

                    // 4. Pantalla de Código de Verificación (Ahora pantalla de "Enlace Enviado")
                    composable("codigo_verificacion") {
                        CodigoVerificacionScreen(
                            onBackToMain = {
                                // Regresa al Login y limpia el historial
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 5. Pantalla de Nueva Contraseña (Opcional si usas link de Firebase)
                    composable("nueva_contrasena") {
                        NuevaContrasenaScreen(
                            onBackClick = { navController.popBackStack() },
                            onConfirmPasswordClick = {
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 6. Pantalla de Registro
                    composable("registro") {
                        RegistroScreen(
                            onBackClick = { navController.popBackStack() },
                            onCreateAccountClick = {
                                navController.navigate("registro_completado")
                            }
                        )
                    }

                    // 7. Pantalla de Registro Completado
                    composable("registro_completado") {
                        RegistroCompletadoScreen(
                            onLoginClick = {
                                navController.navigate("login") {
                                    popUpTo("welcome") { inclusive = true }
                                }
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
        Image(
            painter = painterResource(id = R.drawable.fondo_bienvenidaapp),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    Log.d("RUMBO_DEBUG", "Ejecutando navegación...")
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