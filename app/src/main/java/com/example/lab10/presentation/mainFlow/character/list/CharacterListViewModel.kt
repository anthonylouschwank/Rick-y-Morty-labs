package com.example.lab10.presentation.mainFlow.character.list


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lab10.data.ktor.ApiKtorLab12
import com.example.lab10.data.ktor.Apilab12
import com.example.lab10.data.repository.LocalCharacterRepository
import com.example.lab10.data.source.CharacterDb
import com.example.lab10.domain.repository.CharacterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import com.example.lab10.data.local.dao.CharacterDao
import com.example.lab10.data.local.entity.mapToEntity
import com.example.lab10.data.local.entity.mapToModel
import com.example.lab10.data.ktor.KtorDependencies
import com.example.lab10.di.AppDependencies
import com.example.lab10.data.ktor.Result
import com.example.lab10.data.ktor.mapToCharacterModel
import com.example.lab10.data.local.entity.CharacterEntity
import com.example.lab10.data.model.Character
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class CharacterCollecionUIState(
    val showProgress: Boolean = false,
    val errorMessage: String? = null
)

class CharacterListViewModel(
    private val apiService: Apilab12,
    private val repository: CharacterDao,
    private val backupSource: CharacterDb
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterCollecionUIState())
    val uiState = _uiState.asStateFlow()

    val characters = repository.getAllCharacters()
        .map { list -> list.map { it.mapToModel() } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        refreshFromNetwork()
    }

    private fun loadFromBackup() {
        viewModelScope.launch {
            val backupData = backupSource.getAllCharacters()
            repository.deleteAllCharacters()
            repository.insertCharacters(backupData.map { it.mapToEntity() })
        }
    }

    fun refreshFromNetwork() {
        viewModelScope.launch {
            _uiState.update { it.copy(showProgress = true) }

            when (val result = apiService.getCharacters()) {
                is Result.Success -> {
                    val characters = result.data.results.map { it.mapToCharacterModel() }
                    saveToRepository(characters.map { it.mapToEntity().mapToModel() })
                    println("Network fetch successful")
                }
                is Result.Error -> {
                    loadFromBackup()
                    println("Falling back to local data: ${result.error}")
                }
            }

            delay(4000)
            _uiState.update { it.copy(showProgress = false) }
        }
    }

    private fun saveToRepository(characters: List<Character>) {
        viewModelScope.launch {
            repository.insertCharacters(characters.map { it.mapToEntity()})
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(this[APPLICATION_KEY])
                val database = AppDependencies.provideDatabase(app)
                CharacterCollectionViewModel(
                    apiService = ApiKtorLab12(KtorDependencies.provideHttpClient()),
                    repository = database.characterDao(),
                    backupSource = CharacterDb()
                )
            }
        }
    }
}