package appserviciotecnico.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import appserviciotecnico.ui.screen.FormularioServicioScreen
import appserviciotecnico.ui.screen.HomeScreen
import appserviciotecnico.ui.screen.LoginScreen
import appserviciotecnico.ui.screen.StartScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// 🧭 Composable principal que controla la navegación
@Composable
fun AppNav() {
    val nav = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    NavHost(navController = nav, startDestination = Routes.Start) {
        // LOGO INICIAL (Splash)
        composable(Routes.Start) {
            StartScreen(
                onNavigateToLogin = {
                    nav.navigate(Routes.Login) {
                        popUpTo(Routes.Start) { inclusive = true }
                    }
                }
            )
        }

        // LOGIN
        composable(Routes.Login) {
            LoginScreen(
                onLoginSuccess = {
                    nav.navigate(Routes.Home) {
                        popUpTo(Routes.Login) { inclusive = true } // limpia login del back stack
                        launchSingleTop = true
                    }
                }
            )
        }

        // HOME (Pantalla principal con drawer)
        composable(Routes.Home) {
            DrawerScaffold(
                currentRoute = Routes.Home,
                onNavigate = { nav.navigate(it) },
                drawerState = drawerState,
                scope = scope
            ) {
                HomeScreen()
            }
        }

        // FORMULARIO DE SERVICIO (con drawer)
        composable(Routes.Form) {
            DrawerScaffold(
                currentRoute = Routes.Form,
                onNavigate = { nav.navigate(it) },
                drawerState = drawerState,
                scope = scope
            ) {
                FormularioServicioScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DrawerScaffold(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {
    val destinations = listOf(
        DrawerItem("Inicio", Routes.Home),
        DrawerItem("Solicitar Servicio", Routes.Form)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menú",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                destinations.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            if (currentRoute != item.route) {
                                onNavigate(item.route)
                            }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(appBarTitle(currentRoute)) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { padding ->
            Surface(Modifier.padding(padding)) {
                content()
            }
        }
    }
}

private data class DrawerItem(val label: String, val route: String)

@Composable
private fun appBarTitle(route: String?): String = when (route) {
    Routes.Home -> "Inicio"
    Routes.Form -> "Solicitar Servicio"
    else -> ""
}

