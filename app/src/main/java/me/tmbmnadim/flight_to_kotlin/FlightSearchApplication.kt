package me.tmbmnadim.flight_to_kotlin

import android.app.Application
import me.tmbmnadim.flight_to_kotlin.sources.AppContainer
import me.tmbmnadim.flight_to_kotlin.sources.AppDataContainer

class FlightSearchApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }
}