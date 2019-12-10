package com.cin.ufpe.br.aque.ui.login.student

/**
 * User details post authentication that is exposed to the UI
 */
data class StudentLoggedInUserView(
    val displayName: String,
    val email: String
    //... other data fields that may be accessible to the UI
)
