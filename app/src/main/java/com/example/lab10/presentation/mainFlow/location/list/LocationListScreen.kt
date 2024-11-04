package com.example.lab10.presentation.mainFlow.location.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab10.data.model.Location
import com.example.lab10.presentation.common.ErrorView
import com.example.lab10.presentation.common.LoadingView
import com.example.lab10.presentation.ui.theme.Lab10Theme

@Composable
fun LocationListRoute(
    onLocationClick: (Int) -> Unit,
    viewModel: LocationListViewModel = viewModel(factory = LocationListViewModel.Factory)
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LocationListScreen(
        state = state,
        forceError = { viewModel.onEvent(LocationListEvent.ForceError) },
        onRetryClick = { viewModel.onEvent(LocationListEvent.RetryClick) },
        onLocationClick = onLocationClick,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun LocationListScreen(
    state: LocationListState,
    forceError: () -> Unit,
    onRetryClick: () -> Unit,
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        when {
            state.isLoading -> {
                LoadingView(
                    loadingText = "Obteniendo ubicaciones",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable { forceError() }
                )
            }

            state.isError -> {
                ErrorView(
                    errorText = "Uh, oh. Error al obtener ubicaciones",
                    onRetryClick = onRetryClick,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.locations) { item ->
                        LocationItem(
                            location = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLocationClick(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationItem(
    location: Location,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .padding(16.dp)
    ) {
        Text(text = location.name)
        Text(
            text = location.type,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

private class LocationListParameterProvider : CollectionPreviewParameterProvider<LocationListState> (
    listOf(
        LocationListState(),
        LocationListState(
            isLoading = false,
            isError = true
        ),
        LocationListState(
            isLoading = false,
            locations = listOf(
                Location(
                    id = 1, name = "Jeffery Hartman", type = "consetetur", dimension = "mucius"
                ),
                Location(
                    id = 2, name = "Jeffery Hartman", type = "consetetur", dimension = "mucius"
                )
            )
        )
    )
)

@Preview
@Composable
private fun PreviewLocationListScreen(
    @PreviewParameter(LocationListParameterProvider::class) state: LocationListState
) {
    Lab10Theme() {
        Surface {
            LocationListScreen(
                state = state,
                forceError = {},
                onRetryClick = {},
                onLocationClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}