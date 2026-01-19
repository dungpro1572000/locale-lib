# Android Locale Library

A Jetpack Compose library for handling language/locale switching in Android apps.

## Features

- Easy locale/language switching with AppCompat
- Jetpack Compose UI components for language selection
- Support for multiple languages (EN, KO, ZH, FIL, FR, DE, ES, NL, PT-PT, PT-BR, RU, ID, AF, BN, HI)
- Persistent locale preferences via SharedPreferences
- StateFlow-based reactivity for locale changes
- Built-in logging for debugging

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
    implementation("com.github.dungpro1572000:locale-lib:1.0.2")
}
```

## Usage

### Prerequisites

**IMPORTANT**: Your Activity must extend `AppCompatActivity` for locale switching to work properly.

```kotlin
class MainActivity : AppCompatActivity() {
    // ...
}
```

### Step 1: Initialize LocaleManager

Initialize in your `Application.onCreate()`:

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        LocaleManager.init(this)
    }
}
```

### Step 2: Wrap with LocaleAwareComposable

Wrap your app content with `LocaleAwareComposable`:

```kotlin
@Composable
fun App() {
    LocaleAwareComposable(
        availableLanguages = listOf(
            LanguageItem("en", R.string.language_english, R.drawable.flag_en),
            LanguageItem("vi", R.string.language_vietnamese, R.drawable.flag_vi),
            LanguageItem("ko", R.string.language_korean, R.drawable.flag_ko)
        )
    ) {
        MyAppTheme {
            // Your app content
        }
    }
}
```

### Step 3: Use Language Selection Screen

Navigate to the language selection screen when needed:

```kotlin
// In your navigation or composable
LanguageSelectionScreen(
    onBackClick = {
        // Navigate back after save
        navController.popBackStack()
    },
    onLanguageChanged = { newLanguageCode ->
        // Optional: Handle language change event
        Log.d("App", "Language changed to: $newLanguageCode")
    }
)
```

### Step 4: Access Current Locale (Optional)

You can access the current locale anywhere in your composable tree:

```kotlin
@Composable
fun MyScreen() {
    val localeManager = LocalLocaleManager.current
    val currentLanguageCode = LocalLanguageCode.current
    val currentLocale = LocalAppLocale.current

    // Or use helper functions
    val localeManager2 = rememberLocaleManager()
    val currentLocale2 = rememberCurrentLocale()
    val languageCode = rememberCurrentLanguageCode()
}
```

### Programmatic Locale Change

You can also change locale programmatically:

```kotlin
val localeManager = LocalLocaleManager.current

// Change locale
localeManager.setLocale("ko")  // Switch to Korean
```

## Debugging

The library includes logging with the following tags:

- `LocaleManager` - Logs locale changes, initialization, and SharedPreferences operations
- `LocaleCompositionProvider` - Logs composition/recomposition with locale info
- `LanguageSelection` - Logs user interactions on the language selection screen

Example Logcat filter:
```
LocaleManager|LocaleCompositionProvider|LanguageSelection
```

## LanguageItem

Create language items for your available languages:

```kotlin
data class LanguageItem(
    val code: String,           // Language code (e.g., "en", "vi", "ko")
    val nameResId: Int,         // String resource for display name
    val flagResId: Int          // Drawable resource for flag icon
)
```

## Available Languages (Default)

The library provides default support for:
- English (en)
- Korean (ko)
- Chinese (zh)
- Filipino (fil)
- French (fr)
- German (de)
- Spanish (es)
- Dutch (nl)
- Portuguese - Portugal (pt-PT)
- Portuguese - Brazil (pt-BR)
- Russian (ru)
- Indonesian (in)
- Afrikaans (af)
- Bengali (bn)
- Hindi (hi)

## Common Issues

### Language not changing after save

1. **Activity must extend AppCompatActivity**: The library uses `AppCompatDelegate.setApplicationLocales()` which requires `AppCompatActivity`.

2. **Check Logcat**: Filter by `LocaleManager` tag to see if `setLocale` is being called correctly.

3. **Verify LocaleAwareComposable wrapping**: Ensure your entire app content is wrapped with `LocaleAwareComposable`.

4. **Check string resources**: Make sure you have string resources for each supported language in the appropriate `values-{lang}` folders.

### Recomposition not happening

If UI doesn't update after locale change, check that:
- You're using `stringResource()` for text (not hardcoded strings)
- Your composables are inside `LocaleAwareComposable`
- The Activity will recreate on configuration change

## Author

dungpro1572000
