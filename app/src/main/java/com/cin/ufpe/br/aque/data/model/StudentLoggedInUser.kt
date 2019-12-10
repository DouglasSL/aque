package com.cin.ufpe.br.aque.data.model

/**
 * Data class that captures userStudent information for logged in users retrieved from StudentLoginRepository
 */
data class StudentLoggedInUser(
    val userId: String,
    val displayName: String,
    val email: String
)
