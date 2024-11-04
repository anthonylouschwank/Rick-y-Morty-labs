package com.example.lab10.presentation.mainFlow.location.list

sealed interface LocationListEvent {
    data object ForceError: LocationListEvent
    data object RetryClick: LocationListEvent
}