package me.tmbmnadim.flight_to_kotlin.models

data class Flight (
    val id: Int,
    val from: Airport,
    val to: Airport,
) {
    fun sameRoute(other: Flight): Boolean = from.id == other.from.id && to.id == other.to.id
}