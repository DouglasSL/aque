package com.cin.ufpe.br.aque.models

data class UserLocation(
    val id: String? = null,
    val locations: List<Location>? = emptyList()
)