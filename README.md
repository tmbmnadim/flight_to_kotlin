# Flight Search App

An Android application built with Kotlin and Jetpack Compose that allows users to search for airports, discover flight routes, and save favorite destinations. This project was developed as part of the Android Basics with Compose curriculum to demonstrate local data persistence.

## Features

* **Airport Search:** Search for airports by name or IATA identifier using a dynamic text field.
* **Autocomplete Suggestions:** Real-time database querying provides autocomplete suggestions as the user types.
* **Flight Routes:** Selecting an airport displays available flights to other destinations, showing both IATA codes and full airport names.
* **Favorite Routes:** Save specific flight routes to a favorites list for quick access.
* **Default View:** Displays saved favorite routes automatically when the search query is empty.
* **State Persistence:** Saves the current search text using Preferences DataStore. The search field and corresponding results are automatically restored when reopening the app.

## Tech Stack

* **UI:** Jetpack Compose
* **Language:** Kotlin
* **Database:** Room (SQLite)
* **Local Storage:** Preferences DataStore

## Database Schema

The app uses a pre-populated SQLite database (`flight_search.db`) with two main tables:

### `airport` Table
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | INTEGER | Primary Key |
| `iata_code` | VARCHAR | 3-letter IATA code |
| `name` | VARCHAR | Full airport name |
| `passengers` | INTEGER | Annual passenger count |

### `favorite` Table
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | INTEGER | Primary Key |
| `departure_code` | VARCHAR | Departure airport IATA code |
| `destination_code` | VARCHAR | Destination airport IATA code |

## Getting Started

1. Clone this repository.
2. Open the project in Android Studio.
3. Ensure the pre-populated `flight_search.db` file is located in the project's assets directory.
4. Build and run the application on an emulator or physical device.