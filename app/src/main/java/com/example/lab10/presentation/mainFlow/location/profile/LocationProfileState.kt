package com.example.lab10.presentation.mainFlow.location.profile

import com.example.lab10.data.model.Location

data class LocationProfileState(
    val data: Location? = null,
    val loading: Boolean = false
)