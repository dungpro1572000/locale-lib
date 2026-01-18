# Android Locale Library

A Jetpack Compose library for handling language/locale switching in Android apps.

## Features

- Easy locale/language switching
- Jetpack Compose UI components for language selection
- Support for multiple languages (EN, VI, KO, JA, ZH, TH, FIL, NL, FR, DE, ES, PT, ES-MX)
- Persistent locale preferences

## Installation

### Step 1: Add JitPack repository

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add dependency

Add to your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.dungpro1572000:locale-lib:1.0.0")
}
```

## Usage

### Initialize LocaleManager

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocaleManager.init(this)
    }
}
```

### Use LocaleCompositionProvider

Wrap your app content with `LocaleCompositionProvider`:

```kotlin
@Composable
fun App() {
    LocaleCompositionProvider {
        // Your app content
    }
}
```

### Language Selection Screen

```kotlin
LanguageSelectionScreen(
    onLanguageSelected = { languageItem ->
        // Handle language selection
    }
)
```

## Author

dungpro1572000
