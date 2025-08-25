package com.leandroideas.myapplicationtest

data class LoginState(
    val username: String = "",
    val password: String = "",
    val fieldErrors: List<FieldErrors> = emptyList(),
)

enum class FieldErrors {
    EMPTY_USERNAME,
    EMPTY_PASSWORD,
    INVALID_CREDENTIALS,
}