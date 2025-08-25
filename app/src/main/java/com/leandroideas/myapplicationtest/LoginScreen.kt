package com.leandroideas.myapplicationtest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.leandroideas.myapplicationtest.LoginViewModel.Events.NavigateToAuthenticated
import com.leandroideas.myapplicationtest.ui.theme.MyApplicationTestTheme


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    navigateToWelcomeScreen: () -> Unit,
) {
    val loginState: LoginState by loginViewModel.loginState.collectAsState()
    val usernameErrorMessage = if (loginState.fieldErrors.contains(FieldErrors.EMPTY_USERNAME)) {
        stringResource(R.string.invalid_username)
    } else ""
    val passwordErrorMessage = when {
        loginState.fieldErrors.contains(FieldErrors.EMPTY_PASSWORD) -> stringResource(R.string.invalid_password)
        loginState.fieldErrors.contains(FieldErrors.INVALID_CREDENTIALS) -> stringResource(R.string.invalid_credentials)
        else -> ""
    }
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            loginViewModel.uiEvents.collect { event ->
                when (event) {
                    NavigateToAuthenticated -> navigateToWelcomeScreen()
                }
            }
        }
    }
    LoginComponent(
        modifier = modifier,
        username = loginState.username,
        usernameErrorMessage = usernameErrorMessage,
        password = loginState.password,
        passwordErrorMessage = passwordErrorMessage,
        onUsernameChange = loginViewModel::onUsernameChange,
        onPasswordChange = loginViewModel::onPasswordChange,
        onAuthenticate = loginViewModel::authenticate,
    )
}

@Composable
fun LoginComponent(
    modifier: Modifier = Modifier,
    username: String,
    usernameErrorMessage: String,
    password: String,
    passwordErrorMessage: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onAuthenticate: (String, String) -> Unit,
) {
    // Create a layout for the login screen with EditText fields for username and password, and a login button.
    Column(modifier = modifier) {
        TextField(
            modifier = Modifier.padding(all = 6.dp),
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Username") },
            isError = usernameErrorMessage.isNotEmpty(),
            supportingText = { Text(usernameErrorMessage) },
        )
        TextField(
            modifier = Modifier.padding(all = 6.dp),
            value = password,
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text("Password") },
            isError = passwordErrorMessage.isNotEmpty(),
            supportingText = { Text(passwordErrorMessage) }
        )
        Button(
            modifier = Modifier.padding(all = 6.dp),
            onClick = {
                onAuthenticate(username, password)
            },
        ) {
            Text("Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTestTheme {
        LoginComponent(
            username = "",
            usernameErrorMessage = "",
            password = "",
            passwordErrorMessage = "",
            onUsernameChange = {},
            onPasswordChange = {},
            onAuthenticate = { _, _ -> },
        )
    }
}