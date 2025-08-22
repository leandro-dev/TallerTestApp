package com.leandroideas.myapplicationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.leandroideas.myapplicationtest.ui.theme.MyApplicationTestTheme

class MainActivity : ComponentActivity() {

    /*
    * Requirements:
Create a layout for the login screen with EditText fields for username and password, and a login button.
*
Implement validation to ensure that both username and password fields are not empty.
*
Implement a secure authentication mechanism to verify the username and password. You can use hardcoded credentials for simplicity.

*
* After successful authentication, navigate the user to a welcome screen displaying a welcome message.
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginComponent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoginComponent(modifier: Modifier = Modifier) {
    var isAuthenticated by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var invalidUsername by remember { mutableStateOf(false) }
    var invalidPassword by remember { mutableStateOf(false) }
    if (isAuthenticated) {
        WelcomeComponent(modifier = modifier)
    } else {
        Column(modifier = modifier) {
            TextField(
                modifier = Modifier.padding(all = 6.dp),
                value = username,
                onValueChange = {
                    username = it
                    invalidUsername = false
                },
                label = { Text("Username") },
                isError = invalidUsername,
            )
            TextField(
                modifier = Modifier.padding(all = 6.dp),
                value = password,
                onValueChange = {
                    password = it
                    invalidPassword = false
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text("Password") },
                isError = invalidPassword,
            )
            Button(
                modifier = Modifier.padding(all = 6.dp),
                onClick = {
                    if (username.isEmpty()) {
                        invalidUsername = true
                    }
                    if (password.isEmpty()) {
                        invalidPassword = true
                    }
                    if (!invalidUsername && !invalidPassword) {
                        // Implement a secure authentication mechanism to verify the username and password.
                        // You can use hardcoded credentials for simplicity.
                        if (username == EXPECTED_USER && password == EXPECTED_PASSWORD) {
                            // TODO Navigate
                            isAuthenticated = true
                        }
                    }
                },
            ) {
                Text("Login")
            }
        }
    }
}

@Composable
fun WelcomeComponent(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = "Welcome",
    )
}

const val EXPECTED_USER = "admin"
const val EXPECTED_PASSWORD = "123"

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTestTheme {
        LoginComponent()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    MyApplicationTestTheme {
        WelcomeComponent()
    }
}