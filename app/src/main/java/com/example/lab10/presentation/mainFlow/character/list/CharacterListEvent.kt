package com.example.lab10.presentation.mainFlow.character.list

sealed interface CharacterListEvent {
    data object ForceError: CharacterListEvent
    data object RetryClick: CharacterListEvent
}