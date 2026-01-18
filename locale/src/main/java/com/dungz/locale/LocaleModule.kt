package com.dungz.locale

/**
 * Locale Module - A module for handling language/locale switching in Compose applications.
 *
 * ## Quick Start
 *
 * 1. Add the module dependency in your app's build.gradle.kts:
 *    ```kotlin
 *    implementation(project(":core:locale"))
 *    ```
 *
 * 2. Define your available languages with string resources and flag drawables:
 *    ```kotlin
 *    val languages = listOf(
 *        LanguageItem(R.string.english, R.drawable.flag_en, "en"),
 *        LanguageItem(R.string.vietnamese, R.drawable.flag_vn, "vi"),
 *        LanguageItem(R.string.japanese, R.drawable.flag_jp, "ja")
 *    )
 *    ```
 *
 * 3. Wrap your App composable with LocaleAwareComposable:
 *    ```kotlin
 *    setContent {
 *        LocaleAwareComposable(availableLanguages = languages) {
 *            MyAppTheme {
 *                // Your app content
 *            }
 *        }
 *    }
 *    ```
 *
 * 4. Navigate to LanguageSelectionScreen when user wants to change language:
 *    ```kotlin
 *    LanguageSelectionScreen(
 *        onBackClick = { navController.popBackStack() }
 *    )
 *    ```
 *
 * ## Available APIs
 *
 * ### Data Classes
 * - [LanguageItem] - Data class representing a language with name, flag, and code
 *
 * ### Manager
 * - [LocaleManager] - Singleton manager for handling locale changes with StateFlow
 *
 * ### Composition Locals
 * - [LocalAppLocale] - Provides current Locale
 * - [LocalLocaleManager] - Provides LocaleManager instance
 * - [LocalLanguageCode] - Provides current language code
 *
 * ### Composables
 * - [LocaleAwareComposable] - Wrapper composable that provides locale context
 * - [LanguageSelectionScreen] - Ready-to-use language selection UI
 *
 * ### Helper Functions
 * - [rememberLocaleManager] - Get LocaleManager in composable
 * - [rememberCurrentLocale] - Get current Locale in composable
 * - [rememberCurrentLanguageCode] - Get current language code in composable
 */
object LocaleModule {
    const val VERSION = "1.0.0"
}
