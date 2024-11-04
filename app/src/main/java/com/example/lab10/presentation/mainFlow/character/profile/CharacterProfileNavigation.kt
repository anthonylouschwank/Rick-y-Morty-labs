package com.example.lab10.presentation.mainFlow.character.profile

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
data class CharacterProfileDestination(
    val characterId: Int
)

fun NavController.navigateToCharacterProfileScreen(
    characterId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate(
        route = CharacterProfileDestination(characterId = characterId),
        navOptions = navOptions
    )
}

fun NavGraphBuilder.characterProfileScreen(
    onNavigateBack: () -> Unit
) {
    composable<CharacterProfileDestination> {
        CharacterProfileRoute(onNavigateBack = onNavigateBack)
    }
}