package com.example.rumboapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                val profileViewModel: ProfileViewModel = viewModel()

                // --- ESTADOS GLOBALES (IDA) ---
                var ciudadSeleccionada by remember { mutableStateOf("") }
                var diaSeleccionado by remember { mutableIntStateOf(20) }
                var direccionOrigen by remember { mutableStateOf("") }
                var sillaEscogida by remember { mutableIntStateOf(-1) }

                // --- ESTADOS GLOBALES (REGRESO) ---
                var ciudadRegreso by remember { mutableStateOf("") }
                var diaRegreso by remember { mutableIntStateOf(25) }
                var sillaRegreso by remember { mutableIntStateOf(-1) }

                NavHost(navController = navController, startDestination = "welcome") {

                    composable("welcome") {
                        WelcomeScreen(onIngresarClick = {
                            navController.navigate("login") {
                                launchSingleTop = true
                                restoreState = true
                            }
                        })
                    }

                    composable("login") {
                        LoginScreen(
                            onForgotPasswordClick = { navController.navigate("recupera_password") },
                            onRegisterClick = { navController.navigate("registro") },
                            onLoginSuccess = {
                                profileViewModel.fetchProfileData()
                                navController.navigate("calendario") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // --- AUTH & PERFIL (SIN CAMBIOS) ---
                    composable("recupera_password") { RecuperaPasswordScreen(onBackClick = { navController.popBackStack() }, onNavigateToConfirmation = { navController.navigate("codigo_verificacion") }) }
                    composable("codigo_verificacion") { CodigoVerificacionScreen(onBackToMain = { navController.navigate("login") { popUpTo("login") { inclusive = true } } }) }
                    composable("registro") { RegistroScreen(onBackClick = { navController.popBackStack() }, onCreateAccountClick = { navController.navigate("registro_completado") }) }
                    composable("registro_completado") { RegistroCompletadoScreen(onLoginClick = { navController.navigate("login") { popUpTo("welcome") { inclusive = true } } }) }
                    composable("profile") { ProfileScreen(viewModel = profileViewModel, onBackClick = { navController.popBackStack() }, onHomeClick = { navController.navigate("calendario") }, onEditProfileClick = { navController.navigate("edit_profile") }, onEditAddressClick = { navController.navigate("edit_address") }, onAddAddressClick = { navController.navigate("add_address") }, onAddPhotoClick = { navController.navigate("photo_picker") }, onSelectAvatarClick = { navController.navigate("avatar_picker") }) }
                    composable("edit_profile") { val usuario = profileViewModel.usuario; EditProfileScreen(nombreInicial = usuario?.nombre ?: "", telefonoInicial = usuario?.telefono ?: "", emailInicial = usuario?.email ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { n, t, e -> profileViewModel.updateUsuario(n, t, e) { navController.navigate("profile_updated") } }) }
                    composable("edit_address") { val dir = profileViewModel.usuario?.direcciones?.firstOrNull(); EditAddressScreen(aliasInicial = dir?.alias ?: "", direccionInicial = dir?.descripcion ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { alias, ciudad, desc -> val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf(); if (nuevasDirecciones.isNotEmpty()) nuevasDirecciones[0] = DireccionGuardada(alias, "$ciudad, $desc"); else nuevasDirecciones.add(DireccionGuardada(alias, "$ciudad, $desc")); profileViewModel.updateDirecciones(nuevasDirecciones) { navController.navigate("profile_updated") } }) }
                    composable("add_address") { EditAddressScreen(onBackClick = { navController.popBackStack() }, onGuardarClick = { alias, ciudad, desc -> val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf(); nuevasDirecciones.add(DireccionGuardada(alias, "$ciudad, $desc")); profileViewModel.updateDirecciones(nuevasDirecciones) { navController.navigate("profile_updated") } }) }
                    composable("driver_profile") { DriverProfileScreen(viewModel = profileViewModel, onBackClick = { navController.popBackStack() }, onHomeClick = { navController.navigate("calendario") }, onEditProfileClick = { navController.navigate("edit_driver_profile") }, onEditVehicleClick = { navController.navigate("edit_vehicle") }, onAddPhotoClick = { navController.navigate("photo_picker") }) }
                    composable("edit_driver_profile") { val cond = profileViewModel.conductor; EditDriverProfileScreen(nombreInicial = cond?.nombre ?: "", vehiculoInicial = cond?.vehiculo ?: "", emailInicial = cond?.email ?: "", licenciaInicial = cond?.licencia ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { n, v, e, l -> profileViewModel.updateConductor(n, v, e, l) { navController.navigate("profile_updated") } }) }
                    composable("edit_vehicle") { val veh = profileViewModel.conductor?.vehiculos?.firstOrNull(); EditVehicleScreen(vehiculoInicial = veh?.alias ?: "", placaInicial = veh?.placa ?: "", modeloInicial = veh?.modelo ?: "", anioInicial = veh?.anio ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { v, p, m, a -> val nuevosVehiculos = listOf(VehiculoGuardado(v, p, m, a)); profileViewModel.updateVehiculos(nuevosVehiculos) { navController.navigate("profile_updated") } }) }
                    composable("photo_picker") { PhotoPickerScreen(isLoading = profileViewModel.isLoading, onBackClick = { navController.popBackStack() }, onPhotoSelected = { uri -> if (uri != null) profileViewModel.uploadProfilePhoto(uri) { navController.navigate("profile_updated") } }) }
                    composable("avatar_picker") { AvatarPickerScreen(onBackClick = { navController.popBackStack() }, onAvatarSelected = { avatarName -> profileViewModel.updateAvatar(avatarName) { navController.navigate("profile_updated") } }) }
                    composable("profile_updated") { ProfileUpdatedScreen(onContinueClick = { navController.popBackStack("profile", inclusive = false) }) }

                    // --- FLUJO DE VIAJE (IDA) ---
                    composable("calendario") {
                        CalendarioScreen(
                            onDiaSeleccionado = { dia -> diaSeleccionado = dia; navController.navigate("viaje") },
                            onIrADestino = { navController.navigate("destino") },
                            ciudadDestino = ciudadSeleccionada,
                            direccionOrigen = direccionOrigen,
                            onOrigenCambiado = { direccionOrigen = it }
                        )
                    }

                    composable("destino") {
                        DestinoScreen(
                            onBackClick = { navController.popBackStack() },
                            onCiudadEscogida = { ciudad ->
                                ciudadSeleccionada = ciudad
                                navController.popBackStack()
                            }
                        )
                    }

                    composable("viaje") {
                        ViajeScreen(
                            origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                            destino = ciudadSeleccionada.ifEmpty { "DESTINO" },
                            fecha = "$diaSeleccionado de Marzo",
                            onBackClick = { navController.popBackStack() },
                            onVerSillasClick = { navController.navigate("silla") }
                        )
                    }

                    composable("silla") {
                        SillaScreen(
                            onBackClick = { navController.popBackStack() },
                            onBellClick = { navController.navigate("mensaje") },
                            onConfirmarClick = { numero ->
                                sillaEscogida = numero
                                navController.navigate("resumen")
                            }
                        )
                    }

                    composable("resumen") {
                        ResumenScreen(
                            origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                            destino = ciudadSeleccionada,
                            fecha = "$diaSeleccionado de Marzo",
                            sillaNumero = if (sillaEscogida != -1) sillaEscogida.toString() else "N/A",
                            onBackClick = { navController.popBackStack() },
                            onRegresoClick = { navController.navigate("calendario_regreso") }
                        )
                    }

                    // --- FLUJO DE VIAJE (REGRESO) CORREGIDO ---
                    composable("calendario_regreso") {
                        CalendarioregresoScreen(
                            onDiaSeleccionado = { dia ->
                                diaRegreso = dia
                                navController.navigate("destino_regreso")
                            },
                            onIrADestino = { navController.navigate("destino_regreso") },
                            ciudadDestino = ciudadRegreso,
                            direccionOrigen = direccionOrigen,
                            onOrigenCambiado = { direccionOrigen = it },
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable("destino_regreso") {
                        DestinoregresoScreen(
                            onBackClick = { navController.popBackStack() },
                            onCiudadEscogida = { ciudad ->
                                ciudadRegreso = ciudad
                                navController.navigate("viaje_regreso")
                            }
                        )
                    }

                    composable("viaje_regreso") {
                        ViajeregresoScreen(
                            origen = ciudadSeleccionada.ifEmpty { "ORIGEN" },
                            destino = ciudadRegreso.ifEmpty { "DESTINO" },
                            fecha = "$diaRegreso de Marzo",
                            onBackClick = { navController.popBackStack() },
                            onVerSillasClick = { navController.navigate("silla_regreso") }
                        )
                    }

                    composable("silla_regreso") {
                        SillaregresoScreen(
                            onBackClick = { navController.popBackStack() },
                            onConfirmarClick = { numero ->
                                sillaRegreso = numero
                                navController.navigate("resumen_final")
                            }
                        )
                    }

                    composable("resumen_final") {
                        ResumenScreen(
                            origen = ciudadRegreso,
                            destino = ciudadSeleccionada,
                            fecha = "$diaRegreso de Marzo",
                            sillaNumero = if (sillaRegreso != -1) sillaRegreso.toString() else "N/A",
                            onBackClick = { navController.popBackStack() },
                            onRegresoClick = {
                                navController.navigate("calendario") {
                                    popUpTo("calendario") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("mensaje") {
                        MensajeScreen(onBackClick = { navController.popBackStack() })
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
            modifier = Modifier.fillMaxSize().padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onIngresarClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4AF37)),
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.fillMaxWidth(0.8f).height(55.dp)
            ) {
                Text("INGRESAR", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}