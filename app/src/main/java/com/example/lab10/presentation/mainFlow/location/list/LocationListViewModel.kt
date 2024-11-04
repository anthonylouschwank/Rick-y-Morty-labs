package com.example.lab10.presentation.mainFlow.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab10.data.repository.LocalLocationRepository
import com.example.lab10.domain.repository.LocationRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.lab10.di.AppDependencies
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY

class LocationListViewModel(
    private val locationRepository: LocationRepository
): ViewModel() {
    private var getDataJob: Job? = null
    private var _state = MutableStateFlow(LocationListState())
    val state = _state.asStateFlow()

    init {
        getLocations()
    }

    fun onEvent(event: LocationListEvent) {
        when (event) {
            LocationListEvent.ForceError -> {
                getDataJob?.cancel()
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
            LocationListEvent.RetryClick -> {
                getLocations()
            }
        }
    }

    private fun getLocations() {
        getDataJob = viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false
                )
            }

            val locations = locationRepository.getLocations()

            _state.update { state ->
                state.copy(
                    isLoading = false,
                    locations = locations
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = checkNotNull(this[APPLICATION_KEY])
                val appDatabase = AppDependencies.provideDatabase(context)
                LocationListViewModel(
                    locationRepository = LocalLocationRepository(
                        locationDao = appDatabase.locationDao()
                    )
                )
            }
        }
    }

}