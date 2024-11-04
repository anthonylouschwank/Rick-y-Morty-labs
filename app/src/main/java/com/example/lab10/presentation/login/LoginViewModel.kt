package com.example.lab10.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab10.data.repository.LocalCharacterRepository
import com.example.lab10.data.repository.LocalLocationRepository
import com.example.lab10.di.AppDependencies
import com.example.lab10.domain.repository.CharacterRepository
import com.example.lab10.domain.repository.LocationRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val characterRepository: CharacterRepository,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val _state = MutableStateFlow(LoginScreenState())
    val state = _state.asStateFlow()

    fun onLogin() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            if (characterRepository.initialSync() && locationRepository.initialSync()) {
                _state.update { it.copy(
                    isLoading = false,
                    loginSuccessful = true
                )}
            } else {
                _state.update { it.copy(
                    isLoading = false,
                    loginSuccessful = false
                )}
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val context = checkNotNull(this[APPLICATION_KEY])
                val appDatabase = AppDependencies.provideDatabase(context)
                LoginViewModel(
                    characterRepository = LocalCharacterRepository(appDatabase.characterDao()),
                    locationRepository = LocalLocationRepository(appDatabase.locationDao())
                )
            }
        }
    }
}