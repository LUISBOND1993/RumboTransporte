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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rumboapp.data.InfoTiquete
import com.example.rumboapp.data.MetodoPago
import com.example.rumboapp.ui.theme.RumboAppTheme
import com.example.rumboapp.viewmodel.PagoUiState
import com.example.rumboapp.viewmodel.PagoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RumboAppTheme {
                val navController = rememberNavController()
                val profileViewModel: ProfileViewModel = viewModel()
                val pagoViewModel: PagoViewModel = viewModel()

                // --- ESTADOS GLOBALES (IDA) ---
                var ciudadSeleccionada by remember { mutableStateOf("") }
                var diaSeleccionado by remember { mutableIntStateOf(20) }
                var mesSeleccionado by remember { mutableStateOf("Marzo") }
                var direccionOrigen by remember { mutableStateOf("") }
                var sillaEscogida by remember { mutableIntStateOf(-1) }

                // ESTADOS PARA PRECIO Y HORA DINÁMICOS
                var precioSeleccionado by remember { mutableStateOf("$160.000") }
                var horaSeleccionada by remember { mutableStateOf("08:00 AM") }

                // --- ESTADOS GLOBALES (REGRESO) ---
                var ciudadOrigenRegreso by remember { mutableStateOf("") }
                var direccionDestinoRegreso by remember { mutableStateOf("") }
                var diaRegreso by remember { mutableIntStateOf(25) }
                var mesRegreso by remember { mutableStateOf("Abril") }
                var sillaRegreso by remember { mutableIntStateOf(-1) }

                // --- ESTADOS PARA DIALOGOS DE GUARDADO ---
                var showSaveAddressDialog by remember { mutableStateOf(false) }
                var pendingAddress by remember { mutableStateOf<DireccionGuardada?>(null) }
                
                var showSaveCardDialog by remember { mutableStateOf(false) }
                var pendingCard by remember { mutableStateOf<TarjetaGuardada?>(null) }

                // --- OBSERVADOR DE ESTADO DE PAGO ---
                val pagoState by pagoViewModel.uiState.collectAsState()

                LaunchedEffect(pagoState) {
                    when (val state = pagoState) {
                        is PagoUiState.Exitoso -> {
                            navController.navigate("pago_exitoso") {
                                popUpTo("pago_seleccion") { inclusive = true }
                            }
                        }
                        is PagoUiState.Rechazado -> {
                            navController.navigate("pago_rechazado/${state.motivo}/${state.codigo}/${state.metodoPago.name}")
                        }
                        else -> Unit
                    }
                }

                // Dialogo para guardar dirección
                if (showSaveAddressDialog && pendingAddress != null) {
                    AlertDialog(
                        onDismissRequest = { showSaveAddressDialog = false },
                        containerColor = Color(0xFF2D4B1E),
                        title = { Text("Guardar dirección", color = Color.White) },
                        text = { Text("¿Deseas guardar '${pendingAddress?.descripcion}' en tus direcciones favoritas?", color = Color(0xFFE2E2BD)) },
                        confirmButton = {
                            TextButton(onClick = {
                                val nuevas = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf()
                                if (!nuevas.any { it.descripcion == pendingAddress?.descripcion }) {
                                    nuevas.add(pendingAddress!!)
                                    profileViewModel.updateDirecciones(nuevas) {}
                                }
                                showSaveAddressDialog = false
                            }) { Text("Guardar", color = Color(0xFFD4AF37), fontWeight = FontWeight.Bold) }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSaveAddressDialog = false }) { Text("No, gracias", color = Color.White) }
                        }
                    )
                }

                // Dialogo para guardar tarjeta
                if (showSaveCardDialog && pendingCard != null) {
                    AlertDialog(
                        onDismissRequest = { showSaveCardDialog = false },
                        containerColor = Color(0xFF2D4B1E),
                        title = { Text("Guardar tarjeta", color = Color.White) },
                        text = { Text("¿Deseas guardar esta tarjeta para futuras compras?", color = Color(0xFFE2E2BD)) },
                        confirmButton = {
                            TextButton(onClick = {
                                val nuevas = profileViewModel.usuario?.tarjetas?.toMutableList() ?: mutableListOf()
                                if (!nuevas.any { it.numero == pendingCard?.numero }) {
                                    nuevas.add(pendingCard!!)
                                    profileViewModel.updateTarjetas(nuevas) {}
                                }
                                showSaveCardDialog = false
                            }) { Text("Guardar", color = Color(0xFFD4AF37), fontWeight = FontWeight.Bold) }
                        },
                        dismissButton = {
                            TextButton(onClick = { showSaveCardDialog = false }) { Text("No, gracias", color = Color.White) }
                        }
                    )
                }

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

                    // --- RUTAS DE PERFIL Y REGISTRO ---
                    composable("recupera_password") { RecuperaPasswordScreen(onBackClick = { navController.popBackStack() }, onNavigateToConfirmation = { navController.navigate("codigo_verificacion") }) }
                    composable("codigo_verificacion") { CodigoVerificacionScreen(onBackToMain = { navController.navigate("login") { popUpTo("login") { inclusive = true } } }) }
                    composable("registro") { RegistroScreen(onBackClick = { navController.popBackStack() }, onCreateAccountClick = { navController.navigate("registro_completado") }) }
                    composable("registro_completado") { RegistroCompletadoScreen(onLoginClick = { navController.navigate("login") { popUpTo("welcome") { inclusive = true } } }) }
                    
                    composable("profile") { 
                        ProfileScreen(
                            viewModel = profileViewModel, 
                            onBackClick = { navController.popBackStack() }, 
                            onHomeClick = { navController.navigate("calendario") }, 
                            onEditProfileClick = { navController.navigate("edit_profile") }, 
                            onEditAddressClick = { index ->
                                navController.navigate("edit_address/$index")
                            }, 
                            onAddAddressClick = { navController.navigate("add_address") }, 
                            onAddPhotoClick = { navController.navigate("photo_picker") }, 
                            onSelectAvatarClick = { navController.navigate("avatar_picker") }, 
                            onHistorialClick = { navController.navigate("historial") },
                            onEditCardClick = { index ->
                                navController.navigate("edit_card/$index")
                            },
                            onAddCardClick = { navController.navigate("add_card") }
                        ) 
                    }

                    composable("edit_profile") { val usuario = profileViewModel.usuario; EditProfileScreen(nombreInicial = usuario?.nombre ?: "", telefonoInicial = usuario?.telefono ?: "", emailInicial = usuario?.email ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { n, t, e -> profileViewModel.updateUsuario(n, t, e) { navController.navigate("profile_updated") } }) }
                    
                    composable(
                        "edit_address/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val index = backStackEntry.arguments?.getInt("index") ?: 0
                        val dir = profileViewModel.usuario?.direcciones?.getOrNull(index)
                        if (dir != null) {
                            val partes = dir.descripcion.split(", ")
                            val ciudad = if (partes.size > 1) partes[0] else ""
                            val direccion = if (partes.size > 1) partes.drop(1).joinToString(", ") else dir.descripcion
                            
                            EditAddressScreen(
                                aliasInicial = dir.alias,
                                ciudadInicial = ciudad,
                                direccionInicial = direccion,
                                onBackClick = { navController.popBackStack() },
                                onGuardarClick = { alias, city, desc ->
                                    val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf()
                                    val nuevaDir = DireccionGuardada(alias, if (city.isNotEmpty()) "$city, $desc" else desc)
                                    if (index < nuevasDirecciones.size) {
                                        nuevasDirecciones[index] = nuevaDir
                                        profileViewModel.updateDirecciones(nuevasDirecciones) { navController.navigate("profile_updated") }
                                    }
                                }
                            )
                        }
                    }

                    composable("add_address") { EditAddressScreen(onBackClick = { navController.popBackStack() }, onGuardarClick = { alias, ciudad, desc -> val nuevasDirecciones = profileViewModel.usuario?.direcciones?.toMutableList() ?: mutableListOf(); nuevasDirecciones.add(DireccionGuardada(alias, "$ciudad, $desc")); profileViewModel.updateDirecciones(nuevasDirecciones) { navController.navigate("profile_updated") } }) }
                    
                    composable("add_card") { EditCardScreen(onBackClick = { navController.popBackStack() }, onGuardarClick = { num, nom, fec, cvv -> val nuevas = profileViewModel.usuario?.tarjetas?.toMutableList() ?: mutableListOf(); nuevas.add(TarjetaGuardada(num, nom, fec, cvv)); profileViewModel.updateTarjetas(nuevas) { navController.navigate("profile_updated") } }) }
                    
                    composable(
                        "edit_card/{index}",
                        arguments = listOf(navArgument("index") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val index = backStackEntry.arguments?.getInt("index") ?: 0
                        val tarjetas = profileViewModel.usuario?.tarjetas
                        val tarjeta = tarjetas?.getOrNull(index)
                        if (tarjeta != null) {
                            EditCardScreen(
                                numeroInicial = tarjeta.numero,
                                nombreInicial = tarjeta.nombreTitular,
                                fechaInicial = tarjeta.fechaVencimiento,
                                cvvInicial = tarjeta.cvv,
                                onBackClick = { navController.popBackStack() },
                                onGuardarClick = { n, no, f, c ->
                                    val nuevas = profileViewModel.usuario?.tarjetas?.toMutableList() ?: mutableListOf()
                                    if (index < nuevas.size) {
                                        nuevas[index] = TarjetaGuardada(n, no, f, c)
                                        profileViewModel.updateTarjetas(nuevas) { navController.navigate("profile_updated") }
                                    }
                                }
                            )
                        }
                    }

                    composable("driver_profile") { DriverProfileScreen(viewModel = profileViewModel, onBackClick = { navController.popBackStack() }, onHomeClick = { navController.navigate("calendario") }, onEditProfileClick = { navController.navigate("edit_driver_profile") }, onEditVehicleClick = { navController.navigate("edit_vehicle") }, onAddPhotoClick = { navController.navigate("photo_picker") }) }
                    composable("edit_driver_profile") { val cond = profileViewModel.conductor; EditDriverProfileScreen(nombreInicial = cond?.nombre ?: "", vehiculoInicial = cond?.vehiculo ?: "", emailInicial = cond?.email ?: "", licenciaInicial = cond?.licencia ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { n, v, e, l -> profileViewModel.updateConductor(n, v, e, l) { navController.navigate("profile_updated") } }) }
                    composable("edit_vehicle") { val veh = profileViewModel.conductor?.vehiculos?.firstOrNull(); EditVehicleScreen(vehiculoInicial = veh?.alias ?: "", placaInicial = veh?.placa ?: "", modeloInicial = veh?.modelo ?: "", anioInicial = veh?.anio ?: "", onBackClick = { navController.popBackStack() }, onGuardarClick = { v, p, m, a -> val nuevosVehiculos = listOf(VehiculoGuardado(v, p, m, a)); profileViewModel.updateVehiculos(nuevosVehiculos) { navController.navigate("profile_updated") } }) }
                    composable("photo_picker") { PhotoPickerScreen(isLoading = profileViewModel.isLoading, onBackClick = { navController.popBackStack() }, onPhotoSelected = { uri -> if (uri != null) profileViewModel.uploadProfilePhoto(uri) { navController.navigate("profile_updated") } }) }
                    composable("avatar_picker") { AvatarPickerScreen(onBackClick = { navController.popBackStack() }, onAvatarSelected = { avatarName -> profileViewModel.updateAvatar(avatarName) { navController.navigate("profile_updated") } }) }
                    composable("profile_updated") { ProfileUpdatedScreen(onContinueClick = { navController.popBackStack("profile", inclusive = false) }) }

                    // --- FLUJO DE VIAJE IDA ---
                    composable("calendario") {
                        CalendarioScreen(
                            onDiaSeleccionado = { dia, mes ->
                                diaSeleccionado = dia
                                mesSeleccionado = mes
                                // Preguntar por guardar dirección
                                if (direccionOrigen.isNotEmpty() && !(profileViewModel.usuario?.direcciones?.any { it.descripcion == direccionOrigen } ?: false)) {
                                    pendingAddress = DireccionGuardada("Mi Dirección", direccionOrigen)
                                    showSaveAddressDialog = true
                                }
                                navController.navigate("viaje")
                            },
                            onIrADestino = { navController.navigate("destino") },
                            onBackClick = { navController.popBackStack() },
                            onProfileClick = { navController.navigate("profile") },
                            ciudadDestino = ciudadSeleccionada,
                            direccionOrigen = direccionOrigen,
                            onOrigenCambiado = { direccionOrigen = it },
                            direccionesGuardadas = profileViewModel.usuario?.direcciones ?: emptyList()
                        )
                    }

                    composable("destino") {
                        DestinoScreen(onBackClick = { navController.popBackStack() }, onCiudadEscogida = { ciudad -> ciudadSeleccionada = ciudad; navController.popBackStack() })
                    }

                    composable("viaje") {
                        ViajeScreen(
                            origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                            destino = ciudadSeleccionada.ifEmpty { "DESTINO" },
                            fecha = "$diaSeleccionado de $mesSeleccionado",
                            onBackClick = { navController.popBackStack() },
                            onVerSillasClick = { precio, hora ->
                                precioSeleccionado = precio
                                horaSeleccionada = hora
                                navController.navigate("silla")
                            }
                        )
                    }

                    composable("silla") {
                        SillaScreen(
                            onBackClick = { navController.popBackStack() },
                            onBellClick = { navController.navigate("mensaje") },
                            onConfirmarClick = { numero -> sillaEscogida = numero; navController.navigate("resumen") }
                        )
                    }

                    composable("resumen") {
                        ResumenScreen(
                            origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                            destino = ciudadSeleccionada,
                            fecha = "$diaSeleccionado de $mesSeleccionado",
                            hora = horaSeleccionada,
                            precio = precioSeleccionado,
                            sillaNumero = if (sillaEscogida != -1) sillaEscogida.toString() else "N/A",
                            onBackClick = { navController.popBackStack() },
                            onRegresoClick = {
                                navController.navigate("calendario_regreso")
                            },
                            onConfirmarPagoClick = {
                                val precioL = precioSeleccionado.filter { it.isDigit() }.toLongOrNull() ?: 0L
                                pagoViewModel.setTiquete(InfoTiquete(
                                    origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                                    destino = ciudadSeleccionada,
                                    fecha = "$diaSeleccionado de $mesSeleccionado",
                                    pasajeros = 1,
                                    silla = sillaEscogida.toString(),
                                    total = precioL
                                ))
                                navController.navigate("pago_seleccion")
                            }
                        )
                    }

                    // --- FLUJO DE VIAJE REGRESO ---
                    composable("calendario_regreso") {
                        CalendarioregresoScreen(
                            onDiaSeleccionado = { dia, mes ->
                                diaRegreso = dia
                                mesRegreso = mes
                                // Preguntar por guardar dirección (Destino en regreso)
                                if (direccionDestinoRegreso.isNotEmpty() && !(profileViewModel.usuario?.direcciones?.any { it.descripcion == direccionDestinoRegreso } ?: false)) {
                                    pendingAddress = DireccionGuardada("Destino Frecuente", direccionDestinoRegreso)
                                    showSaveAddressDialog = true
                                }
                                navController.navigate("viaje_regreso")
                            },
                            onOrigenCambiado = { ciudadOrigenRegreso = it },
                            onDestinoCambiado = { direccionDestinoRegreso = it },
                            ciudadOrigen = ciudadOrigenRegreso,
                            direccionDestino = direccionDestinoRegreso,
                            direccionesGuardadas = profileViewModel.usuario?.direcciones ?: emptyList(),
                            onBackClick = { navController.popBackStack() }
                        )
                    }

                    composable("viaje_regreso") {
                        ViajeregresoScreen(
                            origen = ciudadOrigenRegreso.ifEmpty { "ORIGEN" },
                            destino = direccionDestinoRegreso.ifEmpty { "DESTINO" },
                            fecha = "$diaRegreso de $mesRegreso",
                            onBackClick = { navController.popBackStack() },
                            onVerSillasClick = { precio, hora ->
                                precioSeleccionado = precio
                                horaSeleccionada = hora
                                navController.navigate("silla_regreso")
                            }
                        )
                    }

                    composable("silla_regreso") {
                        SillaregresoScreen(
                            onBackClick = { navController.popBackStack() },
                            onConfirmarClick = { numero ->
                                sillaRegreso = numero
                                navController.navigate("resumen_regreso")
                            }
                        )
                    }

                    composable("resumen_regreso") {
                        ResumenregresoScreen(
                            origen = ciudadOrigenRegreso,
                            destino = direccionDestinoRegreso,
                            fecha = "$diaRegreso de $mesRegreso",
                            hora = horaSeleccionada,
                            sillaNumero = if (sillaRegreso != -1) sillaRegreso.toString() else "N/A",
                            precio = precioSeleccionado,
                            onBackClick = { navController.popBackStack() },
                            onConfirmarRegresoClick = { navController.navigate("resumen_final") }
                        )
                    }

                    composable("resumen_final") {
                        ResumenfinalScreen(
                            origenIda = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                            destinoIda = ciudadSeleccionada,
                            fechaIda = "$diaSeleccionado de $mesSeleccionado",
                            horaIda = "08:00 AM",
                            sillaIda = if (sillaEscogida != -1) sillaEscogida.toString() else "N/A",
                            precioIda = "$160.000",
                            origenRegreso = ciudadOrigenRegreso,
                            destinoRegreso = direccionDestinoRegreso,
                            fechaRegreso = "$diaRegreso de $mesRegreso",
                            horaRegreso = horaSeleccionada,
                            sillaRegreso = if (sillaRegreso != -1) sillaRegreso.toString() else "N/A",
                            precioRegreso = precioSeleccionado,
                            onBackClick = { navController.popBackStack() },
                            onConfirmarPagoClick = {
                                val pIda = 160000L
                                val pReg = precioSeleccionado.filter { it.isDigit() }.toLongOrNull() ?: 0L
                                pagoViewModel.setTiquete(InfoTiquete(
                                    origen = direccionOrigen.ifEmpty { "VILLAVICENCIO" },
                                    destino = ciudadSeleccionada,
                                    fecha = "$diaSeleccionado de $mesSeleccionado",
                                    pasajeros = 1,
                                    silla = sillaEscogida.toString(),
                                    total = pIda + pReg
                                ))
                                navController.navigate("pago_seleccion")
                            }
                        )
                    }

                    // --- FLUJO DE PAGOS ---
                    composable("pago_seleccion") {
                        PagoTiqueteScreen(
                            tiquete = pagoViewModel.tiqueteActual,
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { navController.navigate("calendario") { popUpTo("calendario") { inclusive = true } } },
                            onContinuarClick = { metodo ->
                                when (metodo) {
                                    MetodoPago.TARJETA -> navController.navigate("pago_tarjeta")
                                    MetodoPago.PSE -> navController.navigate("pago_pse")
                                    MetodoPago.EFECTIVO -> navController.navigate("pago_efectivo")
                                }
                            }
                        )
                    }

                    composable("pago_tarjeta") {
                        DatosTarjetaScreen(
                            tiquete = pagoViewModel.tiqueteActual,
                            isLoading = pagoState is PagoUiState.Procesando,
                            tarjetasGuardadas = profileViewModel.usuario?.tarjetas ?: emptyList(),
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { navController.navigate("calendario") { popUpTo("calendario") { inclusive = true } } },
                            onPagarClick = { num, nom, fec, cvv, cuo ->
                                pagoViewModel.pagarConTarjeta(num, nom, fec, cvv, cuo)
                                if (!(profileViewModel.usuario?.tarjetas?.any { it.numero == num } ?: false)) {
                                    pendingCard = TarjetaGuardada(num, nom, fec, cvv)
                                    showSaveCardDialog = true
                                }
                            }
                        )
                    }

                    composable("pago_pse") {
                        PagoPSEScreen(
                            tiquete = pagoViewModel.tiqueteActual,
                            isLoading = pagoState is PagoUiState.Procesando,
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { navController.navigate("calendario") { popUpTo("calendario") { inclusive = true } } },
                            onIrAlBancoClick = { ban, tc, tp, doc ->
                                pagoViewModel.pagarConPSE(ban, tc, tp, doc)
                            }
                        )
                    }

                    composable("pago_efectivo") {
                        PagoEfectivoScreen(
                            tiquete = pagoViewModel.tiqueteActual,
                            isLoading = pagoState is PagoUiState.Procesando,
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { navController.navigate("calendario") { popUpTo("calendario") { inclusive = true } } },
                            onConfirmarClick = {
                                pagoViewModel.confirmarEfectivo()
                                profileViewModel.guardarReservaFirebase(
                                    origen = pagoViewModel.tiqueteActual.origen,
                                    destino = pagoViewModel.tiqueteActual.destino,
                                    fecha = pagoViewModel.tiqueteActual.fecha,
                                    silla = pagoViewModel.tiqueteActual.silla,
                                    tipo = "EFECTIVO",
                                    total = pagoViewModel.tiqueteActual.total
                                )
                            },
                            onSeleccionarOtroClick = { navController.popBackStack() }
                        )
                    }

                    composable("pago_exitoso") {
                        val state = pagoState as? PagoUiState.Exitoso
                        if (state != null) {
                            LaunchedEffect(Unit) {
                                profileViewModel.guardarReservaFirebase(
                                    origen = pagoViewModel.tiqueteActual.origen,
                                    destino = pagoViewModel.tiqueteActual.destino,
                                    fecha = pagoViewModel.tiqueteActual.fecha,
                                    silla = pagoViewModel.tiqueteActual.silla,
                                    tipo = state.metodoPago.name,
                                    total = state.total
                                )
                            }
                        }
                        PagoExitosoScreen(
                            referencia = state?.referencia ?: "",
                            total = state?.total ?: 0L,
                            metodoPago = state?.metodoPago ?: MetodoPago.TARJETA,
                            onVerResumenClick = {
                                navController.navigate("historial")
                            },
                            onVolverInicioClick = {
                                navController.navigate("calendario") { popUpTo("welcome") { inclusive = false } }
                            }
                        )
                    }

                    composable("pago_rechazado/{motivo}/{codigo}/{metodo}") { backStackEntry ->
                        val motivo = backStackEntry.arguments?.getString("motivo") ?: ""
                        val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
                        val metodoStr = backStackEntry.arguments?.getString("metodo") ?: "TARJETA"
                        val metodo = try { MetodoPago.valueOf(metodoStr) } catch (e: Exception) { MetodoPago.TARJETA }

                        PagoRechazadoScreen(
                            motivo = motivo,
                            codigo = codigo,
                            metodoPago = metodo,
                            onIntentarNuevoClick = {
                                pagoViewModel.resetearEstado()
                                navController.popBackStack()
                            },
                            onSeleccionarOtroClick = {
                                pagoViewModel.resetearEstado()
                                navController.navigate("pago_seleccion") {
                                    popUpTo("pago_seleccion") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("historial") {
                        HistorialScreen(
                            onBackClick = { navController.popBackStack() },
                            onHomeClick = { navController.navigate("calendario") { popUpTo("calendario") { inclusive = true } } }
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
