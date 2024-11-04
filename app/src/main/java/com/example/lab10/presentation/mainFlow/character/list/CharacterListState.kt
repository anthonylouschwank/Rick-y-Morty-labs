package com.example.lab10.presentation.mainFlow.character.list

import com.example.lab10.data.model.Character

data class CharacterListState(
    val isLoading: Boolean = true,
    val characters: List<Character> = emptyList(),
    val isError: Boolean = false
)