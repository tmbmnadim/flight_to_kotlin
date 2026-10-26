package me.tmbmnadim.flight_to_kotlin

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import me.tmbmnadim.flight_to_kotlin.repositories.FlightSearchPreferencesRepository
import me.tmbmnadim.flight_to_kotlin.sources.AppContainer
import me.tmbmnadim.flight_to_kotlin.sources.AppDataContainer
class FlightSearchApplication: Application() {
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }
}