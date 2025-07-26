package com.chatappfrontend.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chatappfrontend.auth.viewmodel.LoginViewModel
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.designsystem.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onLoginSuccess(event.route)
                }
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::setEmail,
            label = { Text(stringResource(id = R.string.email_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::setPassword,
            label = { Text(stringResource(id = R.string.password_label)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.login_button))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text(stringResource(id = R.string.no_account_sign_up_prompt))
        }
    }
}
