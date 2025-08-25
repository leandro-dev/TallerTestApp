package com.leandroideas.myapplicationtest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState())
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<Events>()
    val uiEvents: SharedFlow<Events> = _uiEvents.asSharedFlow()

    fun onUsernameChange(username: String) {
        val fieldErrors: List<FieldErrors> = loginState.value.fieldErrors.filterNot {
            it == FieldErrors.EMPTY_USERNAME || it == FieldErrors.INVALID_CREDENTIALS
        }
        val newLoginState = loginState.value.copy(
            username = username,
            fieldErrors = fieldErrors,
        )
        _loginState.tryEmit(newLoginState)
    }

    fun onPasswordChange(password: String) {
        val fieldErrors: List<FieldErrors> = loginState.value.fieldErrors.filterNot {
            it == FieldErrors.EMPTY_PASSWORD || it == FieldErrors.INVALID_CREDENTIALS
        }
        val newLoginState = loginState.value.copy(
            password = password,
            fieldErrors = fieldErrors,
        )
        _loginState.tryEmit(newLoginState)
    }

    fun authenticate(username: String, password: String) {
        /*
        * Implement validation to ensure that both username and password fields are not empty.
        * Implement a secure authentication mechanism to verify the username and password.
        * You can use hardcoded credentials for simplicity.
        */
        // TODO This logic should be in another layer, not the ViewModel
        val errors = mutableListOf<FieldErrors>()
        if (username.isEmpty()) {
            errors.add(FieldErrors.EMPTY_USERNAME)
        }
        if (password.isEmpty()) {
            errors.add(FieldErrors.EMPTY_PASSWORD)
        }
        if (username != EXPECTED_USER || password != EXPECTED_PASSWORD) {
            errors.add(FieldErrors.INVALID_CREDENTIALS)
        }

        if (errors.isEmpty()) {
            // After successful authentication, navigate the user to a welcome screen displaying a welcome message.
            viewModelScope.launch {
                _uiEvents.emit(Events.NavigateToAuthenticated)
            }
        } else {
            val newLoginState = loginState.value.copy(
                fieldErrors = errors
            )
            _loginState.tryEmit(newLoginState)
        }
    }

    sealed interface Events {
        object NavigateToAuthenticated : Events
    }

    companion object {
        private const val EXPECTED_USER = "admin"
        private const val EXPECTED_PASSWORD = "123"
    }
}