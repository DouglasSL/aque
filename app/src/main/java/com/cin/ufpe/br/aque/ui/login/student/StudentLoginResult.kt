package com.cin.ufpe.br.aque.ui.login.student

/**
 * Authentication result : success (userStudent details) or error message.
 */
data class StudentLoginResult(
    val success: StudentLoggedInUserView? = null,
    val error: Int? = null
)
