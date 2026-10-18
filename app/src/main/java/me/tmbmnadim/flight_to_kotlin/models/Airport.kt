package me.tmbmnadim.flight_to_kotlin.models

data class Airport(
    val id: Int,
    val name: String,
    val iataCode: String,
    val passengers: Int,
)