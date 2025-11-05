package appserviciotecnico.navigation

// 🔧 Importaciones necesarias para navegación, diseño y estado
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import appserviciotecnico.ui.screen.StartScreen
import kotlinx.coroutines.launch


// 🧭 Composable principal que controla la navegación y el menú lateral
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav() {
    // 🎛 Estado del drawer (menú lateral)
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // 🧵 Scope para lanzar acciones como abrir/cerrar el drawer
    val scope = rememberCoroutineScope()

    // 📋 Lista de ítems del menú lateral
    val destinations = listOf(
        DrawerItem("Inicio", "start") // ← Solo uno por ahora
    )

    // 🧭 Menú lateral (drawer)
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
                        selected = "start" == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    ) {
        // 🧱 Barra superior (top bar) con botón de menú
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Inicio") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { padding ->
            // 🖼 Contenido principal de la pantalla
            StartScreen(modifier = Modifier.padding(padding))
        }
    }
}

// 🧩 Modelo de ítem del menú lateral
private data class DrawerItem(val label: String, val route: String)

