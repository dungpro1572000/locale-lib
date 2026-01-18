# Android Locale Library

A Jetpack Compose library for handling language/locale switching in Android apps.

## Features

- Easy locale/language switching
- Jetpack Compose UI components for language selection
- Support for multiple languages (EN, VI, KO, JA, ZH, TH, FIL, NL, FR, DE, ES, PT, ES-MX)
- Persistent locale preferences

## Installation (Private Repo)

### Step 1: Get JitPack Auth Token

1. Go to https://jitpack.io
2. Sign in with GitHub account: **dungpro1572k**
3. Go to https://jitpack.io/private#auth
4. Copy your Auth Token

### Step 2: Add Auth Token to gradle.properties

Add to your project's `gradle.properties` (or `~/.gradle/gradle.properties` for global):

```properties
authToken=YOUR_JITPACK_AUTH_TOKEN
```

### Step 3: Add JitPack repository

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = "authToken"
                password = providers.gradleProperty("authToken").orNull ?: ""
            }
        }
    }
}
```

### Step 4: Add dependency

Add to your `app/build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.dungpro1572k:android-locale:1.0.0")
}
```

---

## GitHub Credentials (for reference only)

- Username: dungpro1572k
- Password: dung1572000

---

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

dungpro1572k
