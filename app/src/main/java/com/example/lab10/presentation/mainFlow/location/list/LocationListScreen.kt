package com.example.lab10.presentation.mainFlow.location.list

import androidx.lifecycle.viewmodel.compose.viewModel
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab10.data.model.Location
import com.example.lab10.data.source.LocationDb
import com.example.lab10.presentation.mainFlow.character.CharacterViewModel
import com.example.lab10.presentation.mainFlow.location.LocationViewModel
import com.example.lab10.presentation.ui.theme.Lab10Theme
import kotlin.random.Random

@Composable
fun LocationListRoute(
    viewModel: LocationViewModel = viewModel(),
    onLocationClick: (Int) -> Unit,
) {
    val locationDb = LocationDb()
    val locations = locationDb.getAllLocations()
    val randomNum by rememberSaveable {
        mutableIntStateOf(Random.nextInt())
    }

    val isLoading by viewModel.loadingState.collectAsState()
    if (isLoading) {
        Box(
            modifier = androidx.compose.ui.Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Text(text = "Cargando...")
            }
        }
    } else {

        LocationListScreen(

            locations = locations,
            onLocationClick = { locationId ->
                // Iniciamos el proceso de carga y retardo, y luego navegamos
                viewModel.navigateWithDelay(2000L) {
                    onLocationClick(locationId)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun LocationListScreen(
    locations: List<Location>,
    onLocationClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(locations) { item ->
            LocationItem(
                location = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onLocationClick(item.id) }
            )
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewLocationListScreen() {
    Lab10Theme() {
        Surface {
            val db = LocationDb()
            LocationListScreen(
                locations = db.getAllLocations().take(6),
                onLocationClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}