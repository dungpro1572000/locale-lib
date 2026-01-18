package com.dungz.locale.manager

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.dungz.locale.R
import com.dungz.locale.model.LanguageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * Manager class for handling application locale changes.
 * Uses StateFlow to notify observers about locale changes.
 */
class LocaleManager private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    private val _currentLocale = MutableStateFlow(getSavedLocale())
    val currentLocale: StateFlow<Locale> = _currentLocale.asStateFlow()

    private val _currentLanguageCode = MutableStateFlow(getSavedLanguageCode())
    val currentLanguageCode: StateFlow<String> = _currentLanguageCode.asStateFlow()

    /**
     * Available languages for the application.
     * Apps can provide their own list via setAvailableLanguages().
     */
    private var _availableLanguages: List<LanguageItem> = listOf(
        LanguageItem(R.string.language_vietnamese, 0, "vi"),
        LanguageItem(R.string.language_korean, 0, "ko"),
        LanguageItem(R.string.language_english, 0, "en"),
        LanguageItem(R.string.language_japanese, 0, "ja"),
        LanguageItem(R.string.language_chinese, 0, "zh"),
        LanguageItem(R.string.language_thai, 0, "th"),
        LanguageItem(R.string.language_filipino, 0, "fil"),
        LanguageItem(R.string.language_dutch, 0, "nl"),
        LanguageItem(R.string.language_french, 0, "fr"),
        LanguageItem(R.string.language_german, 0, "de"),
        LanguageItem(R.string.language_spanish, 0, "es"),
        LanguageItem(R.string.language_portuguese, 0, "pt"),
        LanguageItem(R.string.language_mexican, 0, "es-MX")
    )
    val availableLanguages: List<LanguageItem>
        get() = _availableLanguages

    /**
     * Set the list of available languages for the application.
     */
    fun setAvailableLanguages(languages: List<LanguageItem>) {
        _availableLanguages = languages
    }

    /**
     * Get the saved language code from preferences.
     */
    private fun getSavedLanguageCode(): String {
        return prefs.getString(KEY_LANGUAGE_CODE, DEFAULT_LANGUAGE_CODE) ?: DEFAULT_LANGUAGE_CODE
    }

    /**
     * Get the saved locale from preferences.
     */
    private fun getSavedLocale(): Locale {
        val languageCode = getSavedLanguageCode()
        return Locale(languageCode)
    }

    /**
     * Change the application locale without destroying the current activity.
     * This uses AppCompatDelegate.setApplicationLocales() which handles the change smoothly.
     *
     * @param languageCode The language code to switch to (e.g., "en", "vi", "ja")
     */
    fun setLocale(languageCode: String) {
        // Save to preferences
        prefs.edit().putString(KEY_LANGUAGE_CODE, languageCode).apply()

        // Update StateFlow values
        val newLocale = Locale(languageCode)
        _currentLocale.value = newLocale
        _currentLanguageCode.value = languageCode

        // Apply locale change using AppCompat (doesn't destroy activity)
        val localeList = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    /**
     * Get the current locale synchronously.
     */
    fun getCurrentLocaleSync(): Locale = _currentLocale.value

    /**
     * Get the current language code synchronously.
     */
    fun getCurrentLanguageCodeSync(): String = _currentLanguageCode.value

    /**
     * Check if a specific language is currently selected.
     */
    fun isLanguageSelected(languageCode: String): Boolean {
        return _currentLanguageCode.value == languageCode
    }

    companion object {
        private const val PREFS_NAME = "locale_prefs"
        private const val KEY_LANGUAGE_CODE = "language_code"
        private const val DEFAULT_LANGUAGE_CODE = "en"

        @Volatile
        private var instance: LocaleManager? = null

        /**
         * Get the singleton instance of LocaleManager.
         */
        fun getInstance(context: Context): LocaleManager {
            return instance ?: synchronized(this) {
                instance ?: LocaleManager(context.applicationContext).also { instance = it }
            }
        }

        /**
         * Initialize LocaleManager. Call this in Application.onCreate().
         */
        fun init(context: Context): LocaleManager {
            return getInstance(context)
        }
    }
}
