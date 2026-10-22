package me.tmbmnadim.flight_to_kotlin.sources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.tmbmnadim.flight_to_kotlin.models.Airport
import me.tmbmnadim.flight_to_kotlin.models.Favorite

@Database(entities = [Airport::class, Favorite::class], exportSchema = false, version = 1)
abstract class FlightSearchDb: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDb? = null

        fun getDatabase(context: Context): FlightSearchDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    name = "flight_search",
                    klass = FlightSearchDb::class.java
                ).createFromAsset("database/flight_search.db")
                    .fallbackToDestructiveMigration(true)
                    .build()
                    .also { Instance = it }

            }
        }
    }
}