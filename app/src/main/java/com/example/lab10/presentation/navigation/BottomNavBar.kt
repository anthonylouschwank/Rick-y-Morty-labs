package com.example.lab10.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import java.lang.reflect.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.input.pointer.motionEventSpy


@Composable
fun BottomNavBar(
    viewModel: NavigationViewModel, // Pasamos el ViewModel para manejar el estado de carga
    checkItemSelected: (Any) -> Boolean,
    onNavItemClick: (Any) -> Unit
) {
    val isLoading by viewModel.loadingState.collectAsState() // Observamos el estado de carga

    if (isLoading) {
        Box(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Cargando...") // Mensaje de carga
            }
        }
    } else {
        // Mostramos el BottomNavBar cuando no estamos cargando
        NavigationBar {
            navigationItems.forEach { navItem ->
                val isItemSelected = checkItemSelected(navItem.destination)
                NavigationBarItem(
                    selected = isItemSelected,
                    label = { Text(navItem.title) },
                    onClick = {
                        // Iniciamos el proceso de retardo y luego navegamos
                        viewModel.navigateWithDelay(4000L) { // 4 segundos de espera
                            onNavItemClick(navItem.destination)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (isItemSelected) {
                                navItem.selectedIcon
                            } else navItem.unselectedIcon,
                            contentDescription = navItem.title
                        )
                    }
                )
            }
        }
    }
}


