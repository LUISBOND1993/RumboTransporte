package com.example.rumboapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
                val profileViewModel: ProfileViewModel = viewModel()

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
                                // Al loguearse, cargamos los datos del perfil
                                profileViewModel.fetchProfileData()
                                navController.navigate("profile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // ... Otras pantallas de auth ...
                    composable("recupera_password") {
                        RecuperaPasswordScreen(
                            onBackClick = { navController.popBackStack() },
                            onNavigateToConfirmation = {
                                navController.navigate("codigo_verificacion")
                            }
                        )
                    }
                    composable("codigo_verificacion") {
                        CodigoVerificacionScreen(
                            onBackToMain = {
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("registro") {
                        RegistroScreen(
                            onBackClick = { navController.popBackStack() },
                            onCreateAccountClick = {
                                navController.navigate("registro_completado")
                            }
                        )
                    }
                    composable("registro_completado") {
                        RegistroCompletadoScreen(
                            onLoginClick = {
                                navController.navigate("login") {
                                    popUpTo("welcome") { inclusive = true }
                                }
                            }
                        )
                    }

                    // ── PERFIL USUARIO ──────────────────────────────
                    composable("profile") {
                        ProfileScreen(
                            viewModel = profileViewModel,
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { /* Navegar a Home si existe */ },
                            onEditProfileClick = { navController.navigate("edit_profile") },
                            onEditAddressClick = { navController.navigate("edit_address") },
                            onAddAddressClick = { navController.navigate("add_address") },
                            onAddPhotoClick = { navController.navigate("photo_picker") },
                            onSelectAvatarClick = { navController.navigate("avatar_picker") }
                        )
                    }

                    composable("edit_profile") {
                        val usuario = profileViewModel.usuario
                        EditProfileScreen(
                            nombreInicial = usuario?.nombre ?: "",
                            telefonoInicial = usuario?.telefono ?: "",
                            emailInicial = usuario?.email ?: "",
                            onBackClick = { navController.popBackStack() },
                            onGuardarClick = { n, t, e ->
                                profileViewModel.updateUsuario(n, t, e) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    composable("edit_address") {
                        // Editamos la primera dirección
                        val dir = profileViewModel.usuario?.direcciones?.firstOrNull()
                        EditAddressScreen(
                            aliasInicial = dir?.alias ?: "",
                            direccionInicial = dir?.descripcion ?: "",
                            onBackClick = { navController.popBackStack() },
                            onGuardarClick = { alias, ciudad, desc ->
                                val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf()
                                if (nuevasDirecciones.isNotEmpty()) {
                                    nuevasDirecciones[0] = DireccionGuardada(alias, "$ciudad, $desc")
                                } else {
                                    nuevasDirecciones.add(DireccionGuardada(alias, "$ciudad, $desc"))
                                }
                                profileViewModel.updateDirecciones(nuevasDirecciones) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    composable("add_address") {
                        EditAddressScreen(
                            onBackClick = { navController.popBackStack() },
                            onGuardarClick = { alias, ciudad, desc ->
                                val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf()
                                nuevasDirecciones.add(DireccionGuardada(alias, "$ciudad, $desc"))
                                profileViewModel.updateDirecciones(nuevasDirecciones) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    // ── PERFIL CONDUCTOR ────────────────────────────
                    composable("driver_profile") {
                        DriverProfileScreen(
                            viewModel = profileViewModel,
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { /* Navegar a Home */ },
                            onEditProfileClick = { navController.navigate("edit_driver_profile") },
                            onEditVehicleClick = { navController.navigate("edit_vehicle") },
                            onAddPhotoClick = { navController.navigate("photo_picker") }
                        )
                    }

                    composable("edit_driver_profile") {
                        val cond = profileViewModel.conductor
                        EditDriverProfileScreen(
                            nombreInicial = cond?.nombre ?: "",
                            vehiculoInicial = cond?.vehiculo ?: "",
                            emailInicial = cond?.email ?: "",
                            licenciaInicial = cond?.licencia ?: "",
                            onBackClick = { navController.popBackStack() },
                            onGuardarClick = { n, v, e, l ->
                                profileViewModel.updateConductor(n, v, e, l) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    composable("edit_vehicle") {
                        val veh = profileViewModel.conductor?.vehiculos?.firstOrNull()
                        EditVehicleScreen(
                            vehiculoInicial = veh?.alias ?: "",
                            placaInicial = veh?.placa ?: "",
                            modeloInicial = veh?.modelo ?: "",
                            anioInicial = veh?.anio ?: "",
                            onBackClick = { navController.popBackStack() },
                            onGuardarClick = { v, p, m, a ->
                                val nuevosVehiculos = listOf(VehiculoGuardado(v, p, m, a))
                                profileViewModel.updateVehiculos(nuevosVehiculos) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    // ── COMUNES ─────────────────────────────────────
                    composable("photo_picker") {
                        PhotoPickerScreen(
                            isLoading = profileViewModel.isLoading,
                            onBackClick = { navController.popBackStack() },
                            onPhotoSelected = { uri ->
                                if (uri != null) {
                                    profileViewModel.uploadProfilePhoto(uri) {
                                        navController.navigate("profile_updated")
                                    }
                                }
                            }
                        )
                    }

                    composable("avatar_picker") {
                        AvatarPickerScreen(
                            onBackClick = { navController.popBackStack() },
                            onAvatarSelected = { avatarName ->
                                profileViewModel.updateAvatar(avatarName) {
                                    navController.navigate("profile_updated")
                                }
                            }
                        )
                    }

                    composable("profile_updated") {
                        ProfileUpdatedScreen(
                            onContinueClick = {
                                navController.popBackStack("profile", inclusive = false)
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
