package com.example.lab10.presentation.mainFlow.character.profile

import com.example.lab10.data.model.Character

data class CharacterProfileState(
    val data: Character? = null,
    val isLoading: Boolean = true
)