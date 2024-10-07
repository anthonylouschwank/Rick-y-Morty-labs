package com.example.lab10.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lab10.presentation.login.LoginDestination
import com.example.lab10.presentation.login.loginScreen
import com.example.lab10.presentation.mainFlow.mainNavigationGraph
import com.example.lab10.presentation.mainFlow.navigateToMainGraph
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    viewModel: NavigationViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = modifier
    ) {

        loginScreen(
            onLoginClick = {

                navController.navigateToMainGraph(
                    navOptions = NavOptions.Builder()
                        .setPopUpTo(LoginDestination, inclusive = true)
                        .build()
                )
            }
        )

        mainNavigationGraph(
            onLogOutClick = {
                // Cuando el usuario hace logout, navegar de regreso al Login
                navController.navigate(LoginDestination) {
                    popUpTo(0) // Eliminar toda la pila de navegación
                }
            }
        )
    }
}