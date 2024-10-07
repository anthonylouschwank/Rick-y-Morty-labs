package com.example.lab10.presentation.mainFlow.character

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.lab10.presentation.mainFlow.character.list.CharacterListDestination
import com.example.lab10.presentation.mainFlow.character.list.characterListScreen
import com.example.lab10.presentation.mainFlow.character.profile.characterProfileScreen
import com.example.lab10.presentation.mainFlow.character.profile.navigateToCharacterProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object CharacterNavGraph

fun NavGraphBuilder.characterGraph(
    navController: NavController
) {
    navigation<CharacterNavGraph>(
        startDestination = CharacterListDestination
    ) {
        characterListScreen(
            onCharacterClick = navController::navigateToCharacterProfileScreen
        )
        characterProfileScreen(
            onNavigateBack = navController::navigateUp
        )
    }
}