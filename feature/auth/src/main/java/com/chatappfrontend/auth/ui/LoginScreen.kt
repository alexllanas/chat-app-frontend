package com.chatappfrontend.auth.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chatappfrontend.auth.viewmodel.LoginViewModel
import com.chatappfrontend.auth.viewmodel.state.LoginUiState
import com.chatappfrontend.common.UiEvent
import com.example.common_android.R

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: (String) -> Unit,
    onNavigateToSignUp: () -> Unit
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

    LoginScreenContent(
        modifier = modifier,
        uiState = uiState,
        setEmail = viewModel::setEmail,
        setPassword = viewModel::setPassword,
        login = viewModel::login,
        onNavigateToSignUp = onNavigateToSignUp
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    setEmail: (String) -> Unit,
    setPassword: (String) -> Unit,
    login: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = setEmail,
            label = { Text(stringResource(id = R.string.email_label)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.password,
            onValueChange = setPassword,
            label = { Text(stringResource(id = R.string.password_label)) },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        ErrorText(errorMessage = uiState.errorMessage)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = login,
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.errorMessage == null || uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            } else {
                Text(stringResource(id = R.string.login_button))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TextButton applies a default content padding: horizontal = 12.dp, vertical = dynamic
        TextButton(onClick = onNavigateToSignUp) {
            Text(stringResource(id = R.string.no_account_sign_up_prompt))
        }
    }
}

@Composable
private fun ErrorText(errorMessage: String?) {
    if (errorMessage != null) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        uiState = LoginUiState(),
        setEmail  = { _ -> },
        setPassword = { _ -> },
        login = {} ,
        onNavigateToSignUp = { },
    )
}