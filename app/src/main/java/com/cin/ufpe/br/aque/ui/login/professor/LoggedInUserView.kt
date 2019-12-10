package com.cin.ufpe.br.aque.ui.login.professor

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val email: String,
    val displayName: String
    //... other data fields that may be accessible to the UI
)
