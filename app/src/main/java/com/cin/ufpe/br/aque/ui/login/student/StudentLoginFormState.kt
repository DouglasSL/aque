package com.cin.ufpe.br.aque.ui.login.student

/**
 * Data validation state of the login form.
 */
data class StudentLoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
