package me.tmbmnadim.flight_to_kotlin.models

data class FlightRoute (
    val id: Int,
    val from: Airport,
    val to: Airport,
) {
    fun isIn(favorites: List<Favorite>) =
        favorites.any { it.departureCode == from.iataCode && it.destinationCode == to.iataCode }
}