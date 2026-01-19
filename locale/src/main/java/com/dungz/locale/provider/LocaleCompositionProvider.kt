package com.dungz.locale.provider

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.dungz.locale.manager.LocaleManager
import com.dungz.locale.model.LanguageItem
import java.util.Locale

private const val TAG = "LocaleCompositionProvider"

/**
 * CompositionLocal for providing the current Locale throughout the composition tree.
 */
val LocalAppLocale = staticCompositionLocalOf<Locale> {
    error("No Locale provided. Wrap your composable with LocaleAwareComposable.")
}

/**
 * CompositionLocal for providing the LocaleManager throughout the composition tree.
 */
val LocalLocaleManager = staticCompositionLocalOf<LocaleManager> {
    error("No LocaleManager provided. Wrap your composable with LocaleAwareComposable.")
}

/**
 * CompositionLocal for providing the current language code.
 */
val LocalLanguageCode = staticCompositionLocalOf<String> {
    error("No language code provided. Wrap your composable with LocaleAwareComposable.")
}

/**
 * A composable wrapper that provides locale-aware context to its children.
 * Wrap your App composable with this to enable locale switching functionality.
 *
 * IMPORTANT: Your Activity must extend AppCompatActivity for locale switching to work.
 *
 * Usage:
 * ```kotlin
 * LocaleAwareComposable(
 *     availableLanguages = listOf(
 *         LanguageItem("en", R.string.english, R.drawable.flag_us),
 *         LanguageItem("vi", R.string.vietnamese, R.drawable.flag_vn)
 *     )
 * ) {
 *     MyAppTheme {
 *         // Your app content
 *     }
 * }
 * ```
 *
 * @param availableLanguages List of available languages for the app
 * @param content The composable content to wrap
 */
@Composable
fun LocaleAwareComposable(
    availableLanguages: List<LanguageItem>,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

    // Get the current locale from configuration (this will trigger recomposition when locale changes)
    val configurationLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        configuration.locale
    }

    Log.d(TAG, "LocaleAwareComposable - Configuration locale: $configurationLocale")
    Log.d(TAG, "LocaleAwareComposable - Configuration locales: ${if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) configuration.locales else "N/A"}")

    val localeManager = remember {
        LocaleManager.getInstance(context).also {
            it.setAvailableLanguages(availableLanguages)
        }
    }

    val currentLocale by localeManager.currentLocale.collectAsState()
    val currentLanguageCode by localeManager.currentLanguageCode.collectAsState()

    Log.d(TAG, "LocaleAwareComposable - LocaleManager currentLocale: $currentLocale")
    Log.d(TAG, "LocaleAwareComposable - LocaleManager currentLanguageCode: $currentLanguageCode")

    // Use configuration locale as the actual locale to ensure UI updates properly
    // The StateFlow will also update, but configuration is the source of truth for actual locale
    val effectiveLocale = configurationLocale ?: currentLocale
    val effectiveLanguageCode = configurationLocale?.language ?: currentLanguageCode

    Log.d(TAG, "LocaleAwareComposable - Effective locale: $effectiveLocale")
    Log.d(TAG, "LocaleAwareComposable - Effective languageCode: $effectiveLanguageCode")

    CompositionLocalProvider(
        LocalAppLocale provides effectiveLocale,
        LocalLocaleManager provides localeManager,
        LocalLanguageCode provides effectiveLanguageCode
    ) {
        content()
    }
}

/**
 * Helper composable to get the current LocaleManager.
 */
@Composable
fun rememberLocaleManager(): LocaleManager {
    return LocalLocaleManager.current
}

/**
 * Helper composable to get the current Locale.
 */
@Composable
fun rememberCurrentLocale(): Locale {
    return LocalAppLocale.current
}

/**
 * Helper composable to get the current language code.
 */
@Composable
fun rememberCurrentLanguageCode(): String {
    return LocalLanguageCode.current
}
