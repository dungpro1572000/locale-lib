package com.dungz.locale.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Data class representing a language item for locale selection.
 *
 * @param name String resource ID for the language name
 * @param flag Drawable resource ID for the language flag
 * @param code Language code (e.g., "en", "vi", "ja")
 */
data class LanguageItem(
    @StringRes val name: Int,
    @DrawableRes val flag: Int,
    val code: String
)
