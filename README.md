# TrackStack
TrackStack is a minimal multi-module Android project built with Kotlin and Jetpack Compose. The sample app uses Material 3 and demonstrates dependency injection with Hilt. The `data` module contains a Room database, DAOs and an injectable repository.

## Modules

- `app` – main application module containing Compose UI.
- `data` – library module for Room-based persistence.
- `po10sync` – library module for background sync routines.
