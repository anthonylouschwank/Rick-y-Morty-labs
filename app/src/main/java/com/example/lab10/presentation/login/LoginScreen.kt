package com.example.lab10.presentation.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uvg.rickandmorty.R
import com.example.lab10.presentation.ui.theme.Lab10Theme


@Composable
fun LoginRoute(
    onSuccessfulLogin: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)
) {
    val state by viewModel.state.collectAsState()
    LoginScreen(
        isLoading = state.isLoading,
        loginSuccessful = state.loginSuccessful,
        onLoginClick = viewModel::onLogin,
        onSuccessfulLogin = onSuccessfulLogin,
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
private fun LoginScreen(
    isLoading: Boolean,
    loginSuccessful: Boolean,
    onLoginClick: () -> Unit,
    onSuccessfulLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(key1 = loginSuccessful) {
        if (loginSuccessful) {
            onSuccessfulLogin()
        }
    }
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.rickmorty_logo), contentDescription = "Logo")
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = {
                    Text(text = "Nombre")
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Iniciar sesión")
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
        }
        Text(
            text = "Anthony Lou Schwank - #23410",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewLoginScreen() {
    Lab10Theme {
        Surface {
            LoginScreen(
                isLoading = true,
                loginSuccessful = false,
                onLoginClick = { },
                onSuccessfulLogin = { },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}