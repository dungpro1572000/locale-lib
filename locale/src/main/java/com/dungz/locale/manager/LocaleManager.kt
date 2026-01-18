package com.dungz.locale.manager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
        LanguageItem("en", R.string.language_english, R.drawable.img_flag_en),
        LanguageItem("ko", R.string.language_korean, R.drawable.img_flag_ko),
        LanguageItem("zh", R.string.language_chinese, R.drawable.img_flag_zh),
        LanguageItem("fil", R.string.language_filipino, R.drawable.img_flag_fil),
        LanguageItem("fr", R.string.language_french, R.drawable.img_flag_fr),
        LanguageItem("de", R.string.language_german, R.drawable.img_flag_de),
        LanguageItem("es", R.string.language_spanish, R.drawable.img_flag_es),
        LanguageItem("nl", R.string.language_dutch, R.drawable.img_flag_nl),
        LanguageItem("pt-PT", R.string.language_portuguese_pt, R.drawable.img_flag_pt_pt),
        LanguageItem("pt-BR", R.string.language_portuguese_br, R.drawable.img_flag_pt_br),
        LanguageItem("ru", R.string.language_russian, R.drawable.img_flag_ru),
        LanguageItem("in", R.string.language_indonesian, R.drawable.img_flag_id),
        LanguageItem("af", R.string.language_afrikaans, R.drawable.img_flag_af),
        LanguageItem("bn", R.string.language_bengali, R.drawable.img_flag_bn),
        LanguageItem("hi", R.string.language_hindi, R.drawable.img_flag_mr)
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
