package com.example.lab10.presentation.mainFlow.location.list

import com.example.lab10.data.model.Location

data class LocationListState(
    val isLoading: Boolean = true,
    val locations: List<Location> = emptyList(),
    val isError: Boolean = false
)