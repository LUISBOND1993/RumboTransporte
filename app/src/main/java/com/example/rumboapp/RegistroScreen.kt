package com.example.rumboapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rumboapp.ui.theme.RumboAppTheme

// Colores definidos según tu diseño
private val VerdeOscuro = Color(0xFF1B3011)
private val ColorCremita = Color(0xFFE2E2BD)
private val ColorDorado = Color(0xFFD4AF37)

@Composable
fun RegistroScreen(onBackClick: () -> Unit, onCreateAccountClick: () -> Unit) {
    // Variables de estado para cada campo de texto
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmaContrasena by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var identificacion by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Imagen de Fondo (Se queda atrás)
        Image(
            painter = painterResource(id = R.drawable.fondo_registro), // Asegúrate que el nombre coincide
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Cubre toda la pantalla
        )

        // 2. Contenedor de contenido (Con Scroll LazyColumn)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp), // Espacio para que el verde no tape los textos superiores
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 30.dp) // Espacio al final para que no pegue el botón
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Ancho del 90% para un margen lateral
                        .background(
                            color = VerdeOscuro,
                            shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp, bottomStart = 45.dp, bottomEnd = 45.dp) // Redondeado en todas las esquinas
                        )
                        .padding(horizontal = 24.dp, vertical = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "¡REGÍSTRATE",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "CON NOSOTROS!",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Reutilizamos la función para crear campos de texto idénticos
                    CampoDeTextoRegistro(titulo = "Nombre Completo:", valor = nombre, alCambiar = { nombre = it })
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "*Ingresa tu email :", valor = email, alCambiar = { email = it })
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "*Ingresa tu contraseña :", valor = contrasena, alCambiar = { contrasena = it }, esContrasena = true)
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "*Confirma nuevamente tu contraseña:", valor = confirmaContrasena, alCambiar = { confirmaContrasena = it }, esContrasena = true)
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "*Ingresa tu Teléfono :", valor = telefono, alCambiar = { telefono = it })
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "Fecha de nacimiento :", valor = fechaNacimiento, alCambiar = { fechaNacimiento = it })
                    Spacer(modifier = Modifier.height(16.dp))

                    CampoDeTextoRegistro(titulo = "¿Cómo te identificas?", valor = identificacion, alCambiar = { identificacion = it })

                    Spacer(modifier = Modifier.height(40.dp))

                    // Botón "Crear Cuenta" (Dorado/Mostaza)
                    Button(
                        onClick = onCreateAccountClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorDorado),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Crear Cuenta",
                            color = Color.Black,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // 3. Botón de Regresar (Flecha arriba a la izquierda)
        Surface(
            modifier = Modifier
                .padding(top = 40.dp, start = 20.dp)
                .size(45.dp),
            color = Color.White.copy(alpha = 0.8f), // Blanco semitransparente
            shape = RoundedCornerShape(50.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_revert), // Icono genérico de atrás
                    contentDescription = "Atrás",
                    tint = Color.Black
                )
            }
        }
    }
}

// Función auxiliar para crear campos de texto reutilizables y limpios
@Composable
fun CampoDeTextoRegistro(
    titulo: String,
    valor: String,
    alCambiar: (String) -> Unit,
    esContrasena: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = titulo,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 4.dp),
            textAlign = TextAlign.Start
        )
        TextField(
            value = valor,
            onValueChange = alCambiar,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = ColorCremita,
                unfocusedContainerColor = ColorCremita,
                disabledContainerColor = ColorCremita,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            visualTransformation = if (esContrasena) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
            singleLine = true
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    RumboAppTheme {
        RegistroScreen(onBackClick = {}, onCreateAccountClick = {})
    }
}