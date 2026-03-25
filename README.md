![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=for-the-badge&logo=material-design&logoColor=white)
![Room](https://img.shields.io/badge/Room-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Koin](https://img.shields.io/badge/Koin-F5A623?style=for-the-badge&logo=koin&logoColor=white)

# TimeTracker

A minimalistic Android time tracking app built with Kotlin and Jetpack Compose. Start and stop a timer to log your activities, browse entries by day, and organize everything with customizable categories.

## Demo & Screenshots

<p align="center">
  <img src="assets/demo.gif" alt="App demo" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="assets/home_page.jpg" alt="Main screen" width="250"/>
  &nbsp;&nbsp;&nbsp;
  <img src="assets/settings_page.jpg" alt="History screen" width="250"/>
</p>

## Features

* **Start & Stop Timer:** Tap to begin tracking, tap again to save the entry with its duration automatically calculated.
* **Category Assignment:** Each entry is assigned to a category, making it easy to see where your time goes.
* **Browse by Day:** Navigate between days to review past entries and their durations.
* **Category Management:** Disable categories you don't need or re-enable them at any time from settings.
* **Clear Data:** Wipe all saved entries directly from the settings screen.

## Tech stack

| Layer | Technology |
|-------|------------|
| Language | **Kotlin** |
| UI | **Jetpack Compose + Material 3** |
| Navigation | **Navigation Compose** |
| Persistence | **Room** |
| DI | **Koin** |

## Build & Run

1. Clone the repository

```
git clone https://github.com/iEranDEV/time-tracker.git
```

2. Open in Android Studio and sync Gradle

3. Run on an emulator or connected device

```
./gradlew installDebug
```
