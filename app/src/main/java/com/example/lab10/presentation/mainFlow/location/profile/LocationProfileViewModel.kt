package com.example.lab10.presentation.mainFlow.location.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.lab10.data.model.Location
import com.example.lab10.data.source.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationProfileViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val locationDb = LocationDb()
    private val locationProfile = savedStateHandle.toRoute<LocationProfileDestination>()
    private val _uiState: MutableStateFlow<LocationProfileState> = MutableStateFlow(LocationProfileState())
    val uiState = _uiState.asStateFlow()

    fun getLocationData() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    loading = true
                )
            }

            delay(5000)
            val location = locationDb.getLocationById(
                locationProfile.locationId
            )

            _uiState.update { state ->
                state.copy(
                    data = location,
                    loading = false
                )
            }

        }

    }
}