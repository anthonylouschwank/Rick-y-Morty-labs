package com.example.lab10.presentation.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class NavigationViewModel : ViewModel() {

    private val _loadingState = MutableStateFlow(false)
    val loadingState = _loadingState.asStateFlow()

    fun navigateWithDelay(
        delayTime: Long,
        onNavigate: () -> Unit
    ) {
        viewModelScope.launch {

            _loadingState.update { true }
            delay(delayTime)
            _loadingState.update { false }

            onNavigate()
        }
    }
}

