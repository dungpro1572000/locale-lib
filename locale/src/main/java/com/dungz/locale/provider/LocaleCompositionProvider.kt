package com.dungz.locale.provider

import android.content.Context
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
 * Usage:
 * ```kotlin
 * LocaleAwareComposable(
 *     availableLanguages = listOf(
 *         LanguageItem(R.string.english, R.drawable.flag_us, "en"),
 *         LanguageItem(R.string.vietnamese, R.drawable.flag_vn, "vi")
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

    val localeManager = remember {
        LocaleManager.getInstance(context).also {
            it.setAvailableLanguages(availableLanguages)
        }
    }

    val currentLocale by localeManager.currentLocale.collectAsState()
    val currentLanguageCode by localeManager.currentLanguageCode.collectAsState()

    CompositionLocalProvider(
        LocalAppLocale provides currentLocale,
        LocalLocaleManager provides localeManager,
        LocalLanguageCode provides currentLanguageCode
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
