package com.example.lab10.presentation.mainFlow.location.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab10.data.model.Location
import com.example.lab10.presentation.ui.theme.Lab10Theme

@Composable
fun LocationProfileRoute(
    onNavigateBack: () -> Unit,
    viewModel: LocationProfileViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    LocationProfileScreen(
        state = state,
        onGetInfoClick = {
            viewModel.getLocationData()
        },
        onNavigateBack = onNavigateBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationProfileScreen(
    state: LocationProfileState,
    onGetInfoClick: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TopAppBar(
            title = {
                Text("Location Details")
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
        LocationProfileContent(
            location = state.data,
            isLoading = state.loading,
            onGetInfoClick = onGetInfoClick,
            modifier = Modifier.fillMaxSize()
        )

    }
}

@Composable
private fun LocationProfileContent(
    location: Location?,
    isLoading: Boolean,
    onGetInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when {
            isLoading -> {
                Text("Cargando, paciente.")
            }

            location == null -> {
                Button(
                    onClick = onGetInfoClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text("Obtener informacion")
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 64.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LocationProfilePropItem(
                        title = "ID:",
                        value = location.id.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    LocationProfilePropItem(
                        title = "Type:",
                        value = location.type,
                        modifier = Modifier.fillMaxWidth()
                    )
                    LocationProfilePropItem(
                        title = "Dimensions:",
                        value = location.dimension,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun LocationProfilePropItem(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        Text(text = value)
    }
}

@Preview
@Composable
private fun PreviewLocationProfileScreen() {
    Lab10Theme() {
        Surface {
            LocationProfileScreen(
                state = LocationProfileState(
                    loading = false,
                    data = Location(1, "Earth (C-137)", "Planet", "Dimension C-137")
                ),
                onNavigateBack = { },
                onGetInfoClick = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLoadingLocationProfileScreen() {
    Lab10Theme() {
        Surface {
            LocationProfileScreen(
                state = LocationProfileState(
                    loading = true,
                    data = null
                ),
                onNavigateBack = { },
                onGetInfoClick = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewEmptyLocationProfileScreen() {
    Lab10Theme() {
        Surface {
            LocationProfileScreen(
                state = LocationProfileState(
                    loading = false,
                    data = null
                ),
                onNavigateBack = { },
                onGetInfoClick = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}