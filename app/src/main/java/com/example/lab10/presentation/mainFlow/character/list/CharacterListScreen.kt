package com.example.lab10.presentation.mainFlow.character.list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab10.data.model.Character
import com.example.lab10.data.source.CharacterDb
import com.example.lab10.presentation.common.ErrorView
import com.example.lab10.presentation.common.LoadingView
import com.example.lab10.presentation.mainFlow.location.list.LocationListEvent
import com.example.lab10.presentation.ui.theme.Lab10Theme
import kotlin.random.Random

@Composable
fun CharacterListRoute(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharacterListViewModel = viewModel(factory = CharacterListViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterListScreen(
        state = state,
        forceError = { viewModel.onEvent(CharacterListEvent.ForceError) },
        onRetryClick = { viewModel.onEvent(CharacterListEvent.RetryClick) },
        onCharacterClick = onCharacterClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun CharacterListScreen(
    state: CharacterListState,
    forceError: () -> Unit,
    onRetryClick: () -> Unit,
    onCharacterClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        when {
            state.isLoading -> {
                LoadingView(
                    loadingText = "Obteniendo personajes",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { forceError() }
                )
            }

            state.isError -> {
                ErrorView(
                    errorText = "Uh, oh. Error al obtener personajes",
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = modifier
                ) {
                    items(state.characters) { item ->
                        CharacterItem(
                            character = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onCharacterClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    val imageBackgroundColors = listOf(
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.inverseSurface
    )
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            modifier = Modifier.size(48.dp),
            color = imageBackgroundColors[(character.id % (imageBackgroundColors.count() - 1))],
            shape = CircleShape
        ) {
            Box {
                Icon(
                    Icons.Outlined.Person, contentDescription = "Image",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Column {
            Text(text = character.name)
            Text(
                text = "${character.species} * ${character.status}",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

private class CharacterListParameterProvider : CollectionPreviewParameterProvider<CharacterListState>(
    listOf(
        CharacterListState(),
        CharacterListState(
            isLoading = false,
            characters = listOf(
                Character(
                    id = 1,
                    name = "Carolyn Salinas",
                    status = "movet",
                    species = "iaculis",
                    gender = "parturient",
                    image = "quidam"
                ),
                Character(
                    id = 2,
                    name = "Carolyn Salinas",
                    status = "movet",
                    species = "iaculis",
                    gender = "parturient",
                    image = "quidam"
                )
            )
        ),
        CharacterListState(
            isLoading = false,
            isError = true
        )
    )
)

@Preview
@Composable
private fun PreviewCharacterListScreen(
    @PreviewParameter(CharacterListParameterProvider::class) state: CharacterListState
) {
    Lab10Theme() {
        Surface {
            CharacterListScreen(
                state = state,
                forceError = {},
                onRetryClick = {},
                onCharacterClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}